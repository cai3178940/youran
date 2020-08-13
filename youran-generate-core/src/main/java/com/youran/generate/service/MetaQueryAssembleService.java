package com.youran.generate.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Sets;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.pojo.po.*;
import com.youran.generate.pojo.po.chart.MetaChartPO;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.MetaChartSourceItemPO;
import com.youran.generate.service.chart.MetaChartService;
import com.youran.generate.service.chart.MetaDashboardService;
import com.youran.generate.service.chart.source.MetaChartSourceService;
import com.youran.generate.service.chart.source.item.MetaChartSourceItemService;
import com.youran.generate.util.MetadataUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 元数据查询装配业务
 *
 * @author: cbb
 * @date: 2017/5/14
 */
@Service
public class MetaQueryAssembleService implements InitializingBean {

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
    @Autowired
    private MetaChartService metaChartService;
    @Autowired
    private MetaChartSourceService metaChartSourceService;
    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private MetaDashboardService metaDashboardService;
    /**
     * 项目元数据缓存
     */
    private Cache<Integer, MetaProjectPO> projectCache;

    /**
     * 获取组装完的项目元数据
     *
     * @param projectId 项目id
     * @param check     是否校验完整性
     * @return
     */
    public MetaProjectPO getAssembledProject(Integer projectId, boolean check) {
        MetaProjectPO project = projectCache.get(projectId, this::doAssembleProject);
        // 校验完整性
        if (check) {
            this.checkAssembledProject(project, true);
        }
        return project;
    }

    /**
     * 装配整个项目的元数据
     *
     * @param projectId 项目id
     * @return
     */
    private MetaProjectPO doAssembleProject(Integer projectId) {
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        // 获取组装后的常量列表
        List<MetaConstPO> metaConstPOS = this.getAllAssembledConsts(projectId, true);
        project.setConsts(metaConstPOS);
        // 查询实体id列表
        List<Integer> entityIds = metaEntityService.findIdsByProject(projectId);
        if (CollectionUtils.isNotEmpty(entityIds)) {
            // 获取组装后的实体列表
            List<MetaEntityPO> metaEntities = entityIds
                .stream()
                .map(entityId -> getAssembledEntity(entityId, true))
                .collect(Collectors.toList());
            project.setEntities(metaEntities);
            // 装配外键实体和外键字段
            this.assembleForeign(metaEntities, true);
            // 查询多对多列表
            List<MetaManyToManyPO> manyToManies = metaManyToManyService.findByProjectId(projectId, true);
            // 装配多对多持有引用
            manyToManies = this.assembleManyToManyWithEntities(metaEntities, manyToManies, true);
            project.setMtms(manyToManies);
            // 装配图表列表
            List<MetaChartPO> charts = this.getAllAssembledCharts(projectId, metaEntities, manyToManies);
            project.setCharts(charts);

            List<MetaDashboardPO> dashboards = Collections.emptyList();
            if (CollectionUtils.isNotEmpty(charts)) {
                dashboards = this.getAllAssembledDashboards(projectId, charts);
            }
            project.setDashboards(dashboards);

            // 校验命名重复的问题
            this.checkNameDuplicate(metaEntities, charts, dashboards);
        }
        return project;
    }

    /**
     * 校验命名重复的问题
     *
     * @param metaEntities
     * @param charts
     * @param dashboards
     */
    private void checkNameDuplicate(List<MetaEntityPO> metaEntities,
                                    List<MetaChartPO> charts,
                                    List<MetaDashboardPO> dashboards) {
        Set<String> names = new HashSet<>();
        if (metaEntities != null) {
            for (MetaEntityPO metaEntity : metaEntities) {
                String name = metaEntity.getClassName().toUpperCase();
                if (names.contains(name)) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体类名冲突【" + metaEntity.getClassName() + "】");
                }
                names.add(name);
            }
        }
        if (charts != null) {
            for (MetaChartPO metaChart : charts) {
                String name = metaChart.getChartName().toUpperCase();
                if (names.contains(name)) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表命名冲突【" + metaChart.getChartName() + "】");
                }
                names.add(name);
            }
        }
        if (dashboards != null) {
            for (MetaDashboardPO metaDashboard : dashboards) {
                String name = metaDashboard.getName().toUpperCase();
                if (names.contains(name)) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "看板命名冲突【" + metaDashboard.getName() + "】");
                }
                names.add(name);
            }
        }
    }

    /**
     * 装配所有图表元数据
     *
     * @param projectId    项目id
     * @param metaEntities 实体列表
     * @param manyToManies
     * @return 装配完成的图表元数据
     */
    private List<MetaChartPO> getAllAssembledCharts(Integer projectId,
                                                    List<MetaEntityPO> metaEntities,
                                                    List<MetaManyToManyPO> manyToManies) {
        // 查询图表列表
        List<MetaChartPO> charts = metaChartService.findByProjectId(projectId, true);
        if (CollectionUtils.isEmpty(charts)) {
            return Collections.emptyList();
        }
        //将实体列表转成map
        Map<Integer, MetaEntityPO> entityMap = metaEntities.stream()
            .collect(Collectors.toMap(MetaEntityPO::getEntityId, e -> e));

        //将多对多列表转成map
        Map<Integer, MetaManyToManyPO> mtmMap = manyToManies.stream()
            .collect(Collectors.toMap(MetaManyToManyPO::getMtmId, e -> e));

        // 查询图表数据源列表
        List<MetaChartSourcePO> chartSources = metaChartSourceService.findByProjectId(projectId);

        //将图表数据源列表转成map
        Map<Integer, MetaChartSourcePO> sourceMap = chartSources.stream()
            .collect(Collectors.toMap(MetaChartSourcePO::getSourceId, e -> e));

        // 查询图表数据项列表
        List<MetaChartSourceItemPO> sourceItems = metaChartSourceItemService.findByProjectId(projectId, true);
        //将图表数据项列表转成map
        Map<Integer, List<MetaChartSourceItemPO>> sourceItemMap = sourceItems.stream()
            .collect(Collectors.groupingBy(MetaChartSourceItemPO::getSourceId));

        for (MetaChartPO chart : charts) {
            MetaChartSourcePO chartSource = sourceMap.get(chart.getSourceId());
            if (chartSource == null) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源不存在，chart=" + chart.getChartName());
            }
            List<MetaChartSourceItemPO> items = sourceItemMap.get(chartSource.getSourceId());
            // 数据源装配数据
            chartSource.assemble(items, entityMap, mtmMap);
            // 图表装配数据
            chart.assemble(chartSource);
        }

        return charts;
    }

    /**
     * 装配所有看板元数据
     *
     * @param projectId 项目id
     * @param charts    图表列表
     * @return 装配完成的看板元数据
     */
    private List<MetaDashboardPO> getAllAssembledDashboards(Integer projectId, List<MetaChartPO> charts) {
        List<MetaDashboardPO> dashboards = metaDashboardService.findByProjectId(projectId);
        if (CollectionUtils.isEmpty(dashboards)) {
            return Collections.emptyList();
        }
        //将图表列表转成map
        Map<Integer, MetaChartPO> chartMap = charts.stream()
            .collect(Collectors.toMap(MetaChartPO::getChartId, e -> e));
        for (MetaDashboardPO dashboard : dashboards) {
            // 看板装配数据
            dashboard.assemble(chartMap);
        }
        return dashboards;
    }

    /**
     * 装配所有常量元数据
     *
     * @param projectId       项目id
     * @param withConstDetail 是否查询枚举值
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
     * 装配枚举元数据
     *
     * @param constId         常量id
     * @param withConstDetail 是否查询枚举值
     * @return
     */
    public MetaConstPO getAssembledConst(Integer constId, boolean withConstDetail) {
        MetaConstPO metaConst = metaConstService.getConst(constId, true);
        if (withConstDetail) {
            List<MetaConstDetailPO> detailList = metaConstDetailService.findByConstId(constId);
            metaConst.setDetailList(detailList);
        }
        return metaConst;
    }

    /**
     * 装配实体元数据
     *
     * @param entityId  实体id
     * @param withIndex 是否需要装配索引
     * @return
     */
    public MetaEntityPO getAssembledEntity(Integer entityId, boolean withIndex) {
        MetaEntityPO metaEntity = metaEntityService.getEntity(entityId, true);
        List<MetaFieldPO> fieldList = metaFieldService.findByEntityId(entityId);
        if (CollectionUtils.isNotEmpty(fieldList)) {
            // 给实体装配字段
            this.assembleFieldForEntity(metaEntity, fieldList);
            // 给实体装配索引
            if (withIndex) {
                this.assembleIndexForEntity(metaEntity, fieldList);
            }
        }
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
            MetadataUtil.checkAndRepairFieldPO(field);
            entity.addField(field);
            String specialField = field.getSpecialField();
            if (field.getPrimaryKey()) {
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
            if (field.getQuery()) {
                entity.addQueryField(field);
            }
            if (field.getInsert()) {
                entity.addInsertField(field);
            }
            if (field.getUpdate()) {
                entity.addUpdateField(field);
            }
            if (field.getList()) {
                entity.addListField(field);
            }
            if (field.getListSort()) {
                entity.addListSortField(field);
            }
            if (field.getShow()) {
                entity.addShowField(field);
            }
            if (field.getForeignKey()) {
                entity.addFkField(field);
            }
            if (Objects.equals(field.getFieldId(), entity.getEntityFeature().getTitleFieldId())) {
                entity.setTitleField(field);
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
                if (metaIndex.getUniqueCheck()) {
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
            // 封装实体的3类多对多关系：持有引用、未持有引用、被对方持有引用
            if (mtm.getHoldRefer1()) {
                entity1.addHold(entity2, mtm);
                entity2.addMtmForOpp(entity1, mtm);
            } else {
                entity1.addUnHold(entity2, mtm);
            }
            if (mtm.getHoldRefer2()) {
                entity2.addHold(entity1, mtm);
                entity1.addMtmForOpp(entity2, mtm);
            } else {
                entity2.addUnHold(entity1, mtm);
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
                if (!metaFieldPO.getForeignKey()) {
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
            if (cascadeExt.getQuery()) {
                cascadeQueryExts.add(cascadeExt);
            }
            if (cascadeExt.getShow()) {
                cascadeShowExts.add(cascadeExt);
            }
            if (cascadeExt.getList()) {
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
        if (CollectionUtils.isEmpty(entities)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "项目中没有实体");
        }
        Map<String, MetaConstPO> constMap = null;
        if (checkConst) {
            // 校验并获取常量Map
            constMap = checkAndGetConstMap(project.getConsts());
        }
        Set<String> defaultConst = Sets.newHashSet();

        for (MetaEntityPO entity : entities) {
            Map<Integer, MetaFieldPO> fields = entity.getFields();
            if (MapUtils.isEmpty(fields)) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】中无字段");
            }
            int pkCount = 0;
            int deletedCount = 0;
            int createdByCount = 0;
            int createdTimeCount = 0;
            int operatedByCount = 0;
            int operatedTimeCount = 0;
            int versionCount = 0;
            for (MetaFieldPO field : fields.values()) {
                String specialField = field.getSpecialField();
                if (field.getPrimaryKey()) {
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
            if (CollectionUtils.isNotEmpty(entity.getForeignEntities())
                && entity.getTitleField() == null) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + entity.getTitle() + "】被外键关联，需要设置标题字段");
            }
            // 校验被持有的多对多实体
            for (MetaEntityPO otherEntity : entity.getHolds().keySet()) {
                if (otherEntity.getTitleField() == null) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "实体【" + otherEntity.getTitle() +
                        "】被【" + entity.getTitle() + "】多对多持有，需要设置标题字段");
                }
            }
        }

        this.checkChart(project);
    }

    private void checkChart(MetaProjectPO project) {
        List<MetaChartPO> charts = project.getCharts();
        if (CollectionUtils.isNotEmpty(charts)) {
            //将实体列表转成map
            Map<Integer, MetaEntityPO> entityMap = project.getEntities().stream()
                .collect(Collectors.toMap(MetaEntityPO::getEntityId, e -> e));
            //将多对多列表转成map
            Map<Integer, MetaManyToManyPO> mtmMap = project.getMtms().stream()
                .collect(Collectors.toMap(MetaManyToManyPO::getMtmId, e -> e));
            for (MetaChartPO chart : charts) {
                chart.check(entityMap, mtmMap);
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
            if (CollectionUtils.isEmpty(constPO.getDetailList())) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "枚举【" + constPO.getConstName() + "】缺少枚举值");
            }
            constMap.put(constPO.getConstName(), constPO);
        }
        return constMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        projectCache = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();
    }

    /**
     * 清除项目元数据缓存
     *
     * @param projectId
     */
    public void invalidate(Integer projectId) {
        projectCache.invalidate(projectId);
    }

}
