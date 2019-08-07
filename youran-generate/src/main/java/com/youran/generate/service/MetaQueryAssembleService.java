package com.youran.generate.service;

import com.google.common.collect.Sets;
import com.youran.common.constant.BoolConst;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.pojo.po.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Title:统一查询组装服务类</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/14
 */
@Service
public class MetaQueryAssembleService {

    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaEntityService metaEntityService;
    @Autowired
    private MetaFieldService metaFieldService;
    @Autowired
    private MetaIndexService metaIndexService;
    @Autowired
    private MetaConstService metaConstService;
    @Autowired
    private MetaConstDetailService metaConstDetailService;
    @Autowired
    private MetaCascadeExtService metaCascadeExtService;
    @Autowired
    private MetaManyToManyService metaManyToManyService;


    public MetaProjectPO getAssembledProject(Integer projectId,boolean withConst,boolean withMtm,boolean withForeign){
        MetaProjectPO project = metaProjectService.getProject(projectId,true);
        // 查询实体id列表
        List<Integer> entityIds = metaEntityService.findIdsByProject(projectId);
        if (CollectionUtils.isEmpty(entityIds)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"项目中没有实体");
        }
        // 获取组装后的实体列表
        List<MetaEntityPO> metaEntities = entityIds
            .stream()
            .map(this::getAssembledEntity).collect(Collectors.toList());
        project.setEntities(metaEntities);
        if(withForeign) {
            // 组装外键实体和外键字段
            this.assembleForeign(metaEntities);
        }
        if(withConst){
            // 查询常量id列表
            List<Integer> constIds = metaConstService.findIdsByProject(projectId);
            // 获取组装后的常量列表
            List<MetaConstPO> metaConstPOS = constIds
                .stream()
                .map(this::getAssembledConst).collect(Collectors.toList());
            project.setConsts(metaConstPOS);
        }
        if(withMtm){
            // 查询多对多列表
            List<MetaManyToManyPO> manyToManies = metaManyToManyService.findByProjectId(projectId);
            // 组装多对多持有引用
            this.assembleManyToManyWithEntities(metaEntities, manyToManies);
            project.setMtms(manyToManies);
        }
        // 校验完整性
        this.checkAssembledProject(project,withConst);
        return project;
    }

    /**
     * 获取组装完成的常量
     * @param constId 常量id
     * @return
     */
    public MetaConstPO getAssembledConst(Integer constId) {
        MetaConstPO metaConst = metaConstService.getConst(constId,true);
        List<MetaConstDetailPO> detailList = metaConstDetailService.findByConstId(constId);
        if (CollectionUtils.isEmpty(detailList)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"枚举【"+metaConst.getConstName()+"】缺少枚举值");
        }
        metaConst.setDetailList(detailList);
        return metaConst;
    }

    /**
     * 获取组装完成的实体
     * @param entityId 实体id
     * @return
     */
    public MetaEntityPO getAssembledEntity(Integer entityId) {
        MetaEntityPO metaEntity = metaEntityService.getEntity(entityId,true);
        return this.assembleEntity(metaEntity);
    }

    /**
     * 组装实体所有关联对象
     * @param metaEntity 实体
     * @return
     */
    public MetaEntityPO assembleEntity(MetaEntityPO metaEntity) {
        Integer entityId = metaEntity.getEntityId();
        List<MetaFieldPO> fieldList = metaFieldService.findByEntityId(entityId);
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体无对应字段，entityId=" + entityId);
        }
        //将list转化为map,并组装实体内部字段
        Map<Integer, MetaFieldPO> fieldMap = new HashMap<>(fieldList.size());
        List<MetaFieldPO> queryFields = new ArrayList<>();
        List<MetaFieldPO> insertFields = new ArrayList<>();
        List<MetaFieldPO> updateFields = new ArrayList<>();
        List<MetaFieldPO> listFields = new ArrayList<>();
        List<MetaFieldPO> listSortFields = new ArrayList<>();
        List<MetaFieldPO> showFields = new ArrayList<>();
        for (MetaFieldPO metaField : fieldList) {
            String specialField = metaField.getSpecialField();
            if (BoolConst.TRUE == metaField.getPrimaryKey()) {
                metaEntity.setPkField(metaField);
            } else if (MetaSpecialField.isDeleted(specialField)) {
                metaEntity.setDelField(metaField);
            } else if (MetaSpecialField.isCreatedBy(specialField)) {
                metaEntity.setCreatedByField(metaField);
            } else if (MetaSpecialField.isCreatedTime(specialField)) {
                metaEntity.setCreatedTimeField(metaField);
            } else if (MetaSpecialField.isOperatedBy(specialField)) {
                metaEntity.setOperatedByField(metaField);
            } else if (MetaSpecialField.isOperatedTime(specialField)) {
                metaEntity.setOperatedTimeField(metaField);
            } else if (MetaSpecialField.isVersion(specialField)) {
                metaEntity.setVersionField(metaField);
            }
            if (BoolConst.TRUE == metaField.getQuery()) {
                queryFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getInsert()) {
                insertFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getUpdate()) {
                updateFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getList()) {
                listFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getListSort()) {
                listSortFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getShow()) {
                showFields.add(metaField);
            }

            fieldMap.put(metaField.getFieldId(), metaField);
        }
        metaEntity.setFields(fieldList);
        metaEntity.setQueryFields(queryFields);
        metaEntity.setInsertFields(insertFields);
        metaEntity.setUpdateFields(updateFields);
        metaEntity.setListFields(listFields);
        metaEntity.setListSortFields(listSortFields);
        metaEntity.setShowFields(showFields);
        List<MetaIndexPO> metaIndexes = metaIndexService.findByEntityId(entityId);
        List<MetaIndexPO> validList = new ArrayList<>();
        List<MetaIndexPO> checkUniqueIndexes = new ArrayList<>();
        //索引中组装字段对象
        if (CollectionUtils.isNotEmpty(metaIndexes)) {
            for (MetaIndexPO metaIndex : metaIndexes) {
                List<Integer> fieldIds = metaIndexService.findFieldIdsByIndexId(metaIndex.getIndexId());
                if (CollectionUtils.isEmpty(fieldIds)) {
                    continue;
                }
                fieldIds.stream().forEach(fieldId -> {
                    MetaFieldPO field = fieldMap.get(fieldId);
                    if (field == null) {
                        throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"索引字段非本实体字段，indexId=" +
                                metaIndex.getIndexId() + ",fieldId=" + fieldId);
                    }
                    metaIndex.addMetaField(field);
                });
                validList.add(metaIndex);
                if(BoolConst.TRUE==metaIndex.getUniqueCheck()){
                    checkUniqueIndexes.add(metaIndex);
                }
            }
        }
        metaEntity.setIndexes(validList);
        metaEntity.setCheckUniqueIndexes(checkUniqueIndexes);
        return metaEntity;
    }

    /**
     * 组装多对多对象引用
     */
    public void assembleManyToManyWithEntities(List<MetaEntityPO> metaEntities, List<MetaManyToManyPO> manyToManies) {
        if (CollectionUtils.isEmpty(manyToManies) || CollectionUtils.isEmpty(metaEntities)) {
            return;
        }
        //将实体列表转成map
        Map<Integer, MetaEntityPO> entityMap = metaEntities.stream()
            .collect(Collectors.toMap(MetaEntityPO::getEntityId, e -> e));
        for (MetaManyToManyPO manyToMany : manyToManies) {
            MetaEntityPO entity1 = entityMap.get(manyToMany.getEntityId1());
            MetaEntityPO entity2 = entityMap.get(manyToMany.getEntityId2());
            // 如果多对多中的实体不在传入的实体列表中，则跳过本次循环
            if(entity1 == null || entity2 == null){
                continue;
            }
            if (BoolConst.FALSE == manyToMany.getHoldRefer1()) {
                entity1.addUnHoldRefer(entity2);
                entity1.addUnHoldMtms(manyToMany);
            } else {
                entity1.addHoldRefer(entity2);
                entity1.addHoldMtms(manyToMany);
            }
            if (BoolConst.FALSE == manyToMany.getHoldRefer2()) {
                entity2.addUnHoldRefer(entity1);
                entity2.addUnHoldMtms(manyToMany);
            } else {
                entity2.addHoldRefer(entity1);
                entity2.addHoldMtms(manyToMany);
            }
            manyToMany.setRefer1(entity1);
            manyToMany.setRefer2(entity2);
        }
    }

    /**
     * 组装外键实体和外键字段
     */
    public void assembleForeign(List<MetaEntityPO> metaEntities){
        for (MetaEntityPO metaEntity : metaEntities) {
            for (MetaFieldPO metaFieldPO : metaEntity.getFields()) {
                //如果不存在外键关系，则跳过
                if(BoolConst.TRUE != metaFieldPO.getForeignKey()){
                    continue;
                }
                //查找当前外键字段对应的外键实体
                MetaEntityPO foreignEntity = this.findMetaEntityById(metaEntities, metaFieldPO.getForeignEntityId());
                if(foreignEntity==null){
                    continue;
                }
                metaFieldPO.setForeignEntity(foreignEntity);
                //获取外键关联的主键字段
                MetaFieldPO foreignField = foreignEntity.getPkField();
                if(!Objects.equals(foreignField.getFieldType(),metaFieldPO.getFieldType())){
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"外键字段"+metaEntity.getTableName()+"."+metaFieldPO.getFieldName()+"与"
                        +foreignEntity.getTableName()+"."+foreignField.getFieldName()+"字段类型不一致");
                }
                if(!Objects.equals(foreignField.getJfieldType(),metaFieldPO.getJfieldType())){
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"java字段"+metaEntity.getClassName()+"."+metaFieldPO.getJfieldName()+"与"
                        +foreignEntity.getClassName()+"."+foreignField.getJfieldName()+"字段类型不一致");
                }
                metaFieldPO.setForeignField(foreignField);
                // 组装级联扩展列表
                this.assembleCascadeExtList(metaFieldPO,foreignEntity.getFields());
                foreignEntity.addForeignField(metaFieldPO);
                foreignEntity.addForeignEntity(metaEntity);
            }
        }
    }


    /**
     * 组装级联扩展列表
     */
    public void assembleCascadeExtList(MetaFieldPO metaFieldPO, List<MetaFieldPO> foreignFields) {
        List<MetaCascadeExtPO> cascadeExts = metaCascadeExtService.findByFieldId(metaFieldPO.getFieldId());
        List<MetaCascadeExtPO> cascadeQueryExts = new ArrayList<>();
        List<MetaCascadeExtPO> cascadeShowExts = new ArrayList<>();
        List<MetaCascadeExtPO> cascadeListExts = new ArrayList<>();
        for (MetaCascadeExtPO cascadeExt : cascadeExts) {
            Optional<MetaFieldPO> first = foreignFields.stream()
                .filter(field -> field.getFieldId().equals(cascadeExt.getCascadeFieldId()))
                .findFirst();
            if(!first.isPresent()) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,metaFieldPO.getFieldDesc()+"的级联扩展字段有误");
            }
            cascadeExt.setCascadeField(first.get());
            if(BoolConst.TRUE==cascadeExt.getQuery()){
                cascadeQueryExts.add(cascadeExt);
            }
            if(BoolConst.TRUE==cascadeExt.getShow()){
                cascadeShowExts.add(cascadeExt);
            }
            if(BoolConst.TRUE==cascadeExt.getList()){
                cascadeListExts.add(cascadeExt);
            }
        }
        metaFieldPO.setCascadeExts(cascadeExts);
        metaFieldPO.setCascadeQueryExts(cascadeQueryExts);
        metaFieldPO.setCascadeShowExts(cascadeShowExts);
        metaFieldPO.setCascadeListExts(cascadeListExts);
    }

    /**
     * 从list中查找实体
     * @param metaEntities
     * @param entityId
     * @return
     */
    private MetaEntityPO findMetaEntityById(List<MetaEntityPO> metaEntities,Integer entityId){
        Optional<MetaEntityPO> first = metaEntities.stream().filter(entityPO -> entityPO.getEntityId().equals(entityId)).findFirst();
        if(first.isPresent()){
            return first.get();
        }
        return null;
    }




    /**
     * 校验组装后的项目完整性
     */
    private void checkAssembledProject(MetaProjectPO project, boolean checkConst) {
        List<MetaEntityPO> entities = project.getEntities();

        Map<String,MetaConstPO> constMap = null;
        if(checkConst) {
            // 校验并获取常量Map
            constMap = checkAndGetConstMap(project.getConsts());
        }
        Set<String> defaultConst = Sets.newHashSet("BoolConst");

        for (MetaEntityPO entity : entities) {
            List<MetaFieldPO> fields = entity.getFields();
            int pkCount = 0;
            int deletedCount = 0;
            int createdByCount = 0;
            int createdTimeCount = 0;
            int operatedByCount = 0;
            int operatedTimeCount = 0;
            int versionCount = 0;
            for (MetaFieldPO field : fields) {
                String specialField = field.getSpecialField();
                if(BoolConst.TRUE == field.getPrimaryKey()){
                    pkCount++;
                    if(StringUtils.isNotBlank(specialField)){
                        throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】的主键【"+field.getFieldDesc()+"】不可以是特殊字段");
                    }
                }
                if (MetaSpecialField.isDeleted(specialField)) {
                    deletedCount++;
                } else if (MetaSpecialField.isCreatedBy(specialField)) {
                    createdByCount++;
                } else if (MetaSpecialField.isCreatedTime(specialField)) {
                    createdTimeCount++;
                } else if (MetaSpecialField.isOperatedBy(specialField)) {
                    operatedByCount++;
                } else if (MetaSpecialField.isOperatedTime(specialField)) {
                    operatedTimeCount++;
                } else if (MetaSpecialField.isVersion(specialField)) {
                    versionCount++;
                }

                if(StringUtils.isNotBlank(field.getDicType())
                    &&checkConst
                    &&!constMap.containsKey(field.getDicType())
                    &&!defaultConst.contains(field.getDicType())){
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】的字段【"+field.getFieldDesc()+"】中指定的枚举字典【"+field.getDicType()+"】不存在");
                }

            }
            if(pkCount==0){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中未找到主键");
            }
            if(pkCount>1){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中存在"+pkCount+"个主键");
            }
            if(deletedCount>1){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中存在"+deletedCount+"个逻辑删除字段");
            }
            if(createdByCount>1){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中存在"+createdByCount+"个创建人员字段");
            }
            if(createdTimeCount>1){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中存在"+createdTimeCount+"个创建时间字段");
            }
            if(operatedByCount>1){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中存在"+operatedByCount+"个更新人员字段");
            }
            if(operatedTimeCount>1){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中存在"+operatedTimeCount+"个更新时间字段");
            }
            if(versionCount>1){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"实体【"+entity.getTitle()+"】中存在"+versionCount+"个乐观锁版本字段");
            }

        }


    }


    /**
     * 校验并获取常量Map
     * @param consts
     * @return
     */
    private Map<String, MetaConstPO> checkAndGetConstMap(List<MetaConstPO> consts) {
        Map<String, MetaConstPO> constMap = new HashMap<>(consts.size());
        for (MetaConstPO constPO : consts) {
            if(constMap.containsKey(constPO.getConstName())){
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"枚举类名冲突："+constPO.getConstName());
            }
            constMap.put(constPO.getConstName(),constPO);
        }
        return constMap;
    }
}
