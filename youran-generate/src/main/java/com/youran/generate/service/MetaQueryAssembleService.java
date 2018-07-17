package com.youran.generate.service;

import com.google.common.collect.Sets;
import com.youran.common.constant.BoolConst;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.po.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Title: 统一查询组装服务类
 * Description:
 * Author: cbb
 * Create Time:2017/5/14 10:45
 */
@Service
public class MetaQueryAssembleService {

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

    /**
     * 获取组装完成的常量
     * @param constId 常量id
     * @return
     */
    public MetaConstPO getAssembledConst(Integer constId) {
        MetaConstPO metaConst = metaConstService.getConst(constId,true);
        List<MetaConstDetailPO> detailList = metaConstDetailService.findByConstId(constId);
        if (CollectionUtils.isEmpty(detailList)) {
            throw new GenerateException("常量无对应常量值，constId=" + constId);
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
            throw new GenerateException("实体无对应字段，entityId=" + entityId);
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
            } else if (Objects.equals(specialField, MetaSpecialField.DEL_SIGN)) {
                metaEntity.setDelField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.CREATE_BY)) {
                metaEntity.setCreateByField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.CREATE_DATE)) {
                metaEntity.setCreateDateField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_BY)) {
                metaEntity.setOperateByField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_DATE)) {
                metaEntity.setOperateDateField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.VERSION)) {
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
                    //throw new GenerateException("索引有误，缺少索引字段，entityId=" + entityId + ",indexId=" + metaIndex.getIndexId());
                    continue;
                }
                fieldIds.stream().forEach(fieldId -> {
                    MetaFieldPO field = fieldMap.get(fieldId);
                    if (field == null) {
                        throw new GenerateException("索引字段非本实体字段，indexId=" +
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
                    throw new GenerateException("外键字段"+metaEntity.getTableName()+"."+metaFieldPO.getFieldName()+"与"
                        +foreignEntity.getTableName()+"."+foreignField.getFieldName()+"字段类型不一致");
                }
                if(!Objects.equals(foreignField.getJfieldType(),metaFieldPO.getJfieldType())){
                    throw new GenerateException("java字段"+metaEntity.getClassName()+"."+metaFieldPO.getJfieldName()+"与"
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
                throw new GenerateException(metaFieldPO.getFieldDesc()+"的级联扩展字段有误");
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
    public void checkAssembledProject(MetaProjectPO project, boolean checkConst) {
        List<MetaEntityPO> entities = project.getEntities();

        Map<String,MetaConstPO> constMap = null;
        if(checkConst) {
            List<MetaConstPO> consts = project.getConsts();
            constMap = new HashMap<>(consts.size());
            for (MetaConstPO constPO : consts) {
                if(constMap.containsKey(constPO.getConstName())){
                    throw new GenerateException("枚举类名冲突："+constPO.getConstName());
                }
                constMap.put(constPO.getConstName(),constPO);
            }
        }
        Set<String> defaultConst = Sets.newHashSet("BoolConst");

        for (MetaEntityPO entity : entities) {
            List<MetaFieldPO> fields = entity.getFields();
            int pkCount = 0;
            int delSignCount = 0;
            int createByCount = 0;
            int createDateCount = 0;
            int operateByCount = 0;
            int operateDateCount = 0;
            int versionCount = 0;
            for (MetaFieldPO field : fields) {
                String specialField = field.getSpecialField();
                if(BoolConst.TRUE == field.getPrimaryKey()){
                    pkCount++;
                    if(StringUtils.isNotBlank(specialField)){
                        throw new GenerateException("实体【"+entity.getTitle()+"】的主键【"+field.getFieldDesc()+"】不可以是特殊字段");
                    }
                }
                if (Objects.equals(specialField, MetaSpecialField.DEL_SIGN)) {
                    delSignCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.CREATE_BY)) {
                    createByCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.CREATE_DATE)) {
                    createDateCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_BY)) {
                    operateByCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_DATE)) {
                    operateDateCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.VERSION)) {
                    versionCount++;
                }

                if(StringUtils.isNotBlank(field.getDicType())
                    &&checkConst
                    &&!constMap.containsKey(field.getDicType())
                    &&!defaultConst.contains(field.getDicType())){
                    throw new GenerateException("实体【"+entity.getTitle()+"】的字段【"+field.getFieldDesc()+"】中指定的枚举字典【"+field.getDicType()+"】不存在");
                }

            }
            if(pkCount==0){
                throw new GenerateException("实体【"+entity.getTitle()+"】中未找到主键");
            }
            if(pkCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+pkCount+"个主键");
            }
            if(delSignCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+delSignCount+"个逻辑删除字段");
            }
            if(createByCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+createByCount+"个创建人员字段");
            }
            if(createDateCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+createDateCount+"个创建时间字段");
            }
            if(operateByCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+operateByCount+"个更新人员字段");
            }
            if(operateDateCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+operateDateCount+"个更新时间字段");
            }
            if(versionCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+versionCount+"个乐观锁版本字段");
            }

        }


    }
}
