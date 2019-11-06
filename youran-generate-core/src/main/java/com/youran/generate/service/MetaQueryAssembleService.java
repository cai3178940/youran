package com.youran.generate.service;

import com.google.common.collect.Sets;
import com.youran.common.constant.BoolConst;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.pojo.po.*;
import com.youran.generate.util.MetadataUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 元数据查询装配业务
 *
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
    private MetaMtmCascadeExtService metaMtmCascadeExtService;
    @Autowired
    private MetaManyToManyService metaManyToManyService;


    /**
     * 装配整个项目的元数据
     *
     * @param projectId      项目id
     * @param withConst      是否需要装配常量
     * @param withMtm        是否需要装配多对多
     * @param withForeign    是否需要装配外键关联
     * @param withFkCascade  是否装配外键级联扩展
     * @param withMtmCascade 是否装配多对多级联扩展
     * @param check          是否校验完整性
     * @return
     */
    public MetaProjectPO getAssembledProject(Integer projectId, boolean withConst,
                                             boolean withMtm, boolean withForeign,
                                             boolean withFkCascade, boolean withMtmCascade,
                                             boolean check) {
        MetaProjectPO project = metaProjectService.getProject(projectId, true);
        // 查询实体id列表
        List<Integer> entityIds = metaEntityService.findIdsByProject(projectId);
        if (CollectionUtils.isEmpty(entityIds)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "项目中没有实体");
        }
        // 获取组装后的实体列表
        List<MetaEntityPO> metaEntities = entityIds
            .stream()
            .map(this::getAssembledEntity).collect(Collectors.toList());
        project.setEntities(metaEntities);
        if (withForeign) {
            // 装配外键实体和外键字段
            this.assembleForeign(metaEntities, withFkCascade);
        }
        if (withConst) {
            // 获取组装后的常量列表
            List<MetaConstPO> metaConstPOS = this.getAllAssembledConsts(projectId, true);
            project.setConsts(metaConstPOS);
        }
        if (withMtm) {
            // 查询多对多列表
            List<MetaManyToManyPO> manyToManies = metaManyToManyService.findByProjectId(projectId, true);
            // 装配多对多持有引用
            manyToManies = this.assembleManyToManyWithEntities(metaEntities, manyToManies, withMtmCascade);
            project.setMtms(manyToManies);
        }
        // 校验完整性
        if (check) {
            this.checkAssembledProject(project, withConst);
        }
        return project;
    }

    /**
     * 装配所有常量元数据
     *
     * @param projectId 项目id
     * @return
     */
    public List<MetaConstPO> getAllAssembledConsts(Integer projectId, boolean withConstDetail) {
        // 查询常量id列表
        List<Integer> constIds = metaConstService.findIdsByProject(projectId);
        // 返回组装后的常量列表
        return constIds
            .stream()
            .map(constId -> this.getAssembledConst(constId, withConstDetail))
            .collect(Collectors.toList());
    }

    /**
     * 装配常量元数据
     *
     * @param constId 常量id
     * @return
     */
    public MetaConstPO getAssembledConst(Integer constId, boolean withConstDetail) {
        MetaConstPO metaConst = metaConstService.getConst(constId, true);
        if (withConstDetail) {
            List<MetaConstDetailPO> detailList = metaConstDetailService.findByConstId(constId);
            if (CollectionUtils.isEmpty(detailList)) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "枚举【" + metaConst.getConstName() + "】缺少枚举值");
            }
            metaConst.setDetailList(detailList);
        }
        return metaConst;
    }

    /**
     * 装配实体元数据
     *
     * @param entityId 实体id
     * @return
     */
    public MetaEntityPO getAssembledEntity(Integer entityId) {
        MetaEntityPO metaEntity = metaEntityService.getEntity(entityId, true);
        List<MetaFieldPO> fieldList = metaFieldService.findByEntityId(entityId);
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体中无字段，entityId=" + entityId);
        }
        // 给实体装配字段
        this.assembleFieldForEntity(metaEntity, fieldList);
        // 给实体装配索引
        this.assembleIndexForEntity(metaEntity, fieldList);
        return metaEntity;
    }

    /**
     * 给实体装配字段
     *
     * @param entity    实体
     * @param fieldList 字段列表
     * @return
     */
    private void assembleFieldForEntity(MetaEntityPO entity, List<MetaFieldPO> fieldList) {
        for (MetaFieldPO field : fieldList) {
            entity.addField(field);
            String specialField = field.getSpecialField();
            if (BoolConst.isTrue(field.getPrimaryKey())) {
                entity.setPkField(field);
            } else if (MetaSpecialField.isDeleted(specialField)) {
                entity.setDelField(field);
            } else if (MetaSpecialField.isCreatedBy(specialField)) {
                entity.setCreatedByField(field);
            } else if (MetaSpecialField.isCreatedTime(specialField)) {
                entity.setCreatedTimeField(field);
            } else if (MetaSpecialField.isOperatedBy(specialField)) {
                entity.setOperatedByField(field);
            } else if (MetaSpecialField.isOperatedTime(specialField)) {
                entity.setOperatedTimeField(field);
            } else if (MetaSpecialField.isVersion(specialField)) {
                entity.setVersionField(field);
            }
            if (BoolConst.isTrue(field.getQuery())) {
                entity.addQueryField(field);
            }
            if (BoolConst.isTrue(field.getInsert())) {
                entity.addInsertField(field);
            }
            if (BoolConst.isTrue(field.getUpdate())) {
                entity.addUpdateField(field);
            }
            if (BoolConst.isTrue(field.getList())) {
                entity.addListField(field);
            }
            if (BoolConst.isTrue(field.getListSort())) {
                entity.addListSortField(field);
            }
            if (BoolConst.isTrue(field.getShow())) {
                entity.addShowField(field);
            }
            if (BoolConst.isTrue(field.getForeignKey())) {
                entity.addFkField(field);
            }
        }
    }

    /**
     * 给实体装配索引
     *
     * @param metaEntity 实体
     * @param fieldList  字段列表
     */
    private void assembleIndexForEntity(MetaEntityPO metaEntity, List<MetaFieldPO> fieldList) {
        //将list转化为map
        Map<Integer, MetaFieldPO> fieldMap = fieldList.stream()
            .collect(Collectors.toMap(MetaFieldPO::getFieldId, f -> f));
        List<MetaIndexPO> metaIndexes = metaIndexService.findByEntityId(metaEntity.getEntityId());
        //有效索引列表
        List<MetaIndexPO> validList = new ArrayList<>();
        //需要校验唯一性的索引列表
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
                        throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "索引字段非本实体字段，indexId=" +
                            metaIndex.getIndexId() + ",fieldId=" + fieldId);
                    }
                    metaIndex.addMetaField(field);
                });
                validList.add(metaIndex);
                if (BoolConst.isTrue(metaIndex.getUniqueCheck())) {
                    checkUniqueIndexes.add(metaIndex);
                }
            }
        }
        metaEntity.setIndexes(validList);
        metaEntity.setCheckUniqueIndexes(checkUniqueIndexes);
    }


    /**
     * 装配多对多对象引用
     *
     * @param metaEntities   实体列表
     * @param manyToManies   多对多列表
     * @param withMtmCascade 是否装配多对多级联扩展
     * @return 装配及过滤后的多对多列表
     */
    public List<MetaManyToManyPO> assembleManyToManyWithEntities(List<MetaEntityPO> metaEntities,
                                                                 List<MetaManyToManyPO> manyToManies,
                                                                 boolean withMtmCascade) {
        List<MetaManyToManyPO> result = new ArrayList<>(manyToManies.size());
        if (CollectionUtils.isEmpty(manyToManies) || CollectionUtils.isEmpty(metaEntities)) {
            return result;
        }
        //将实体列表转成map
        Map<Integer, MetaEntityPO> entityMap = metaEntities.stream()
            .collect(Collectors.toMap(MetaEntityPO::getEntityId, e -> e));
        for (MetaManyToManyPO mtm : manyToManies) {
            MetaEntityPO entity1 = entityMap.get(mtm.getEntityId1());
            MetaEntityPO entity2 = entityMap.get(mtm.getEntityId2());
            // 如果多对多中的实体不在传入的实体列表中，则跳过本次循环
            if (entity1 == null || entity2 == null) {
                continue;
            }
            if (BoolConst.isFalse(mtm.getHoldRefer1())) {
                entity1.addUnHold(entity2, mtm);
            } else {
                entity1.addHold(entity2, mtm);
            }
            if (BoolConst.isFalse(mtm.getHoldRefer2())) {
                entity2.addUnHold(entity1, mtm);
            } else {
                entity2.addHold(entity1, mtm);
            }
            mtm.setRefer1(entity1);
            mtm.setRefer2(entity2);
            //装配多对多级联扩展
            if (withMtmCascade) {
                this.assembleMtmCascadeExt(mtm, entityMap);
            }
            result.add(mtm);
        }
        return result;
    }

    /**
     * 给多对多装配级联扩展
     *
     * @param mtm     待装配的多对多
     * @param entitys 所有实体
     */
    private void assembleMtmCascadeExt(MetaManyToManyPO mtm, Map<Integer, MetaEntityPO> entitys) {
        MetaEntityPO entity1 = mtm.getRefer1();
        MetaEntityPO entity2 = mtm.getRefer2();
        List<MetaMtmCascadeExtPO> cascadeExtList1 = metaMtmCascadeExtService.findByMtmIdAndEntityId(mtm.getMtmId(),
            entity1.getEntityId());
        List<MetaMtmCascadeExtPO> cascadeExtList2 = metaMtmCascadeExtService.findByMtmIdAndEntityId(mtm.getMtmId(),
            entity2.getEntityId());
        // 过滤并装配多对对级联扩展
        List<MetaMtmCascadeExtPO> filteredCascadeExtList1 = this.filterAndAssembleMtmCascadeExt(cascadeExtList1, entitys);
        List<MetaMtmCascadeExtPO> filteredCascadeExtList2 = this.filterAndAssembleMtmCascadeExt(cascadeExtList2, entitys);
        mtm.setCascadeExtList1(filteredCascadeExtList1);
        mtm.setCascadeExtList2(filteredCascadeExtList2);
        mtm.setFkAliasForSql1(MetadataUtil.getMtmFkAlias(mtm, entity1, true));
        mtm.setFkAliasForSql2(MetadataUtil.getMtmFkAlias(mtm, entity2, true));
        mtm.setFkAliasForJava1(MetadataUtil.getMtmFkAlias(mtm, entity1, false));
        mtm.setFkAliasForJava2(MetadataUtil.getMtmFkAlias(mtm, entity2, false));
    }

    /**
     * 过滤并装配多对对级联扩展
     *
     * @param entitys
     * @param cascadeExtList
     * @return
     */
    private List<MetaMtmCascadeExtPO> filterAndAssembleMtmCascadeExt(List<MetaMtmCascadeExtPO> cascadeExtList,
                                                                     Map<Integer, MetaEntityPO> entitys) {
        List<MetaMtmCascadeExtPO> filteredCascadeExtList = new ArrayList<>();
        for (MetaMtmCascadeExtPO cascadeExtPO : cascadeExtList) {
            Integer cascadeEntityId = cascadeExtPO.getCascadeEntityId();
            Integer cascadeFieldId = cascadeExtPO.getCascadeFieldId();
            MetaEntityPO cascadeEntity = entitys.get(cascadeEntityId);
            if (cascadeEntity == null) {
                continue;
            }
            MetaFieldPO cascadeField = cascadeEntity.getFields().get(cascadeFieldId);
            if (cascadeField == null) {
                continue;
            }
            cascadeExtPO.setCascadeEntity(cascadeEntity);
            cascadeExtPO.setCascadeField(cascadeField);
            cascadeExtPO.setHostEntity(entitys.get(cascadeExtPO.getEntityId()));
            filteredCascadeExtList.add(cascadeExtPO);
        }
        return filteredCascadeExtList;
    }


    /**
     * 装配外键实体和外键字段
     *
     * @param metaEntities  实体列表
     * @param withFkCascade 是否装配外键级联扩展
     */
    public void assembleForeign(List<MetaEntityPO> metaEntities, boolean withFkCascade) {
        for (MetaEntityPO metaEntity : metaEntities) {
            for (MetaFieldPO metaFieldPO : metaEntity.getFields().values()) {
                //如果不存在外键关系，则跳过
                if (BoolConst.isFalse(metaFieldPO.getForeignKey())) {
                    continue;
                }
                //查找当前外键字段对应的外键实体
                MetaEntityPO foreignEntity = this.findMetaEntityById(metaEntities, metaFieldPO.getForeignEntityId());
                if (foreignEntity == null) {
                    continue;
                }
                metaFieldPO.setForeignEntity(foreignEntity);
                //获取外键关联的主键字段
                MetaFieldPO foreignField = foreignEntity.getPkField();
                if (!Objects.equals(foreignField.getFieldType(), metaFieldPO.getFieldType())) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "外键字段" + metaEntity.getTableName() + "." + metaFieldPO.getFieldName() + "与"
                        + foreignEntity.getTableName() + "." + foreignField.getFieldName() + "字段类型不一致");
                }
                if (!Objects.equals(foreignField.getJfieldType(), metaFieldPO.getJfieldType())) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "java字段" + metaEntity.getClassName() + "." + metaFieldPO.getJfieldName() + "与"
                        + foreignEntity.getClassName() + "." + foreignField.getJfieldName() + "字段类型不一致");
                }
                metaFieldPO.setForeignField(foreignField);
                // 装配级联扩展列表
                if (withFkCascade) {
                    this.assembleCascadeExtList(metaFieldPO, foreignEntity.getFields());
                }
                foreignEntity.addForeignField(metaFieldPO);
                foreignEntity.addForeignEntity(metaEntity);
            }
        }
    }


    /**
     * 装配外键级联扩展列表
     */
    public void assembleCascadeExtList(MetaFieldPO metaFieldPO, Map<Integer, MetaFieldPO> foreignFields) {
        List<MetaCascadeExtPO> cascadeExts = metaCascadeExtService.findByFieldId(metaFieldPO.getFieldId());
        List<MetaCascadeExtPO> cascadeQueryExts = new ArrayList<>();
        List<MetaCascadeExtPO> cascadeShowExts = new ArrayList<>();
        List<MetaCascadeExtPO> cascadeListExts = new ArrayList<>();
        for (MetaCascadeExtPO cascadeExt : cascadeExts) {
            MetaFieldPO field = foreignFields.get(cascadeExt.getCascadeFieldId());
            if (field == null) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, metaFieldPO.getFieldDesc() + "的级联扩展字段有误");
            }
            cascadeExt.setCascadeField(field);
            if (BoolConst.isTrue(cascadeExt.getQuery())) {
                cascadeQueryExts.add(cascadeExt);
            }
            if (BoolConst.isTrue(cascadeExt.getShow())) {
                cascadeShowExts.add(cascadeExt);
            }
            if (BoolConst.isTrue(cascadeExt.getList())) {
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
     *
     * @param metaEntities
     * @param entityId
     * @return
     */
    private MetaEntityPO findMetaEntityById(List<MetaEntityPO> metaEntities, Integer entityId) {
        Optional<MetaEntityPO> first = metaEntities.stream()
            .filter(entityPO -> entityPO.getEntityId().equals(entityId)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }


    /**
     * 校验组装后的项目完整性
     */
    private void checkAssembledProject(MetaProjectPO project, boolean checkConst) {
        List<MetaEntityPO> entities = project.getEntities();

        Map<String, MetaConstPO> constMap = null;
        if (checkConst) {
            // 校验并获取常量Map
            constMap = checkAndGetConstMap(project.getConsts());
        }
        Set<String> defaultConst = Sets.newHashSet("BoolConst");

        for (MetaEntityPO entity : entities) {
            Map<Integer, MetaFieldPO> fields = entity.getFields();
            int pkCount = 0;
            int deletedCount = 0;
            int createdByCount = 0;
            int createdTimeCount = 0;
            int operatedByCount = 0;
            int operatedTimeCount = 0;
            int versionCount = 0;
            for (MetaFieldPO field : fields.values()) {
                String specialField = field.getSpecialField();
                if (BoolConst.isTrue(field.getPrimaryKey())) {
                    pkCount++;
                    if (StringUtils.isNotBlank(specialField)) {
                        throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】的主键【" + field.getFieldDesc() + "】不可以是特殊字段");
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

                if (StringUtils.isNotBlank(field.getDicType())
                    && checkConst
                    && !constMap.containsKey(field.getDicType())
                    && !defaultConst.contains(field.getDicType())) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】的字段【" + field.getFieldDesc() + "】中指定的枚举字典【" + field.getDicType() + "】不存在");
                }

            }
            if (pkCount == 0) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中未找到主键");
            }
            if (pkCount > 1) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中存在" + pkCount + "个主键");
            }
            if (deletedCount > 1) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中存在" + deletedCount + "个逻辑删除字段");
            }
            if (createdByCount > 1) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中存在" + createdByCount + "个创建人员字段");
            }
            if (createdTimeCount > 1) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中存在" + createdTimeCount + "个创建时间字段");
            }
            if (operatedByCount > 1) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中存在" + operatedByCount + "个更新人员字段");
            }
            if (operatedTimeCount > 1) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中存在" + operatedTimeCount + "个更新时间字段");
            }
            if (versionCount > 1) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中存在" + versionCount + "个乐观锁版本字段");
            }

        }


    }


    /**
     * 校验并获取常量Map
     *
     * @param consts
     * @return
     */
    private Map<String, MetaConstPO> checkAndGetConstMap(List<MetaConstPO> consts) {
        Map<String, MetaConstPO> constMap = new HashMap<>(consts.size());
        for (MetaConstPO constPO : consts) {
            if (constMap.containsKey(constPO.getConstName())) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "枚举类名冲突：" + constPO.getConstName());
            }
            constMap.put(constPO.getConstName(), constPO);
        }
        return constMap;
    }
}
