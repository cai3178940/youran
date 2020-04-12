package com.youran.generate.pojo.po.chart.source;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.ChartSourceFeatureDTO;
import com.youran.generate.pojo.dto.chart.source.JoinDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import com.youran.generate.pojo.po.BasePO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.po.chart.source.item.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 图表数据源
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartSourcePO extends BasePO {

    /**
     * 主键ID
     */
    private Integer sourceId;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 特性json
     */
    private String feature;

    /**
     * 是否聚合
     */
    private Boolean aggregation;


    /**
     * 主实体id
     */
    private Integer entityId;

    /**
     * 关联
     */
    private List<JoinDTO> joins;

    /**
     * 数量限制
     */
    private Integer limit;

    /**
     * 主实体
     */
    @JsonIgnore
    private transient MetaEntityPO entity;

    /**
     * 明细列
     */
    @JsonIgnore
    private transient Map<Integer, DetailColumnPO> detailColumnMap;
    /**
     * 维度
     */
    @JsonIgnore
    private transient Map<Integer, DimensionPO> dimensionMap;
    /**
     * 指标
     */
    @JsonIgnore
    private transient Map<Integer, MetricsPO> metricsMap;
    /**
     * where条件
     */
    @JsonIgnore
    private transient Map<Integer, WherePO> whereMap;
    /**
     * having条件
     */
    @JsonIgnore
    private transient Map<Integer, HavingPO> havingMap;
    /**
     * 明细排序
     */
    @JsonIgnore
    private transient Map<Integer, DetailOrderPO> detailOrderMap;
    /**
     * 聚合排序
     */
    @JsonIgnore
    private transient Map<Integer, AggOrderPO> aggOrderMap;

    /**
     * 装配数据
     *
     * @param items
     * @param entityMap
     * @param mtmMap
     */
    public void assemble(List<? extends MetaChartSourceItemPO> items,
                         Map<Integer, MetaEntityPO> entityMap,
                         Map<Integer, MetaManyToManyPO> mtmMap) {
        // 装配实体和joins
        this.assembleEntityAndJoins(entityMap, mtmMap);

        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        // 对图表数据项按类型分组
        Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map = items.stream()
            .collect(Collectors.groupingBy(e -> e.getType()));
        // 装配明细列
        this.assembleDetailColumnList(map);
        // 装配维度
        this.assembleDimensionList(map);
        // 装配指标
        this.assembleMetricsList(map);
        // 装配where条件
        this.assembleWhereList(map);
        // 装配having条件
        this.assembleHavingList(map);
        // 装配明细排序
        this.assembleDetailOrderList(map);
        // 装配聚合排序
        this.assembleAggOrderList(map);
    }

    /**
     * 装配明细列
     *
     * @param map
     */
    private void assembleDetailColumnList(Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map) {
        List<DetailColumnPO> detailColumnList = (List<DetailColumnPO>) map.get(SourceItemType.DETAIL_COLUMN.getValue());
        if (detailColumnList != null) {
            this.detailColumnMap = detailColumnList.stream()
                .peek(item -> item.assembleItem(this))
                .collect(Collectors.toMap(MetaChartSourceItemPO::getSourceItemId, Function.identity()));
        } else {
            this.detailColumnMap = new HashMap<>();
        }
    }

    /**
     * 装配维度
     *
     * @param map
     */
    private void assembleDimensionList(Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map) {
        List<DimensionPO> dimensionList = (List<DimensionPO>) map.get(SourceItemType.DIMENSION.getValue());
        if (dimensionList != null) {
            this.dimensionMap = dimensionList.stream()
                .peek(item -> item.assembleItem(this))
                .collect(Collectors.toMap(MetaChartSourceItemPO::getSourceItemId, Function.identity()));
        } else {
            this.dimensionMap = new HashMap<>();
        }
    }

    /**
     * 装配指标
     *
     * @param map
     */
    private void assembleMetricsList(Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map) {
        List<MetricsPO> metricsList = (List<MetricsPO>) map.get(SourceItemType.METRICS.getValue());
        if (metricsList != null) {
            this.metricsMap = metricsList.stream()
                .peek(item -> item.assembleItem(this))
                .collect(Collectors.toMap(MetaChartSourceItemPO::getSourceItemId, Function.identity()));
        } else {
            this.metricsMap = new HashMap<>();
        }
    }

    /**
     * 装配where条件
     *
     * @param map
     */
    private void assembleWhereList(Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map) {
        List<WherePO> whereList = (List<WherePO>) map.get(SourceItemType.WHERE.getValue());
        if (whereList != null) {
            this.whereMap = whereList.stream()
                .peek(item -> item.assembleItem(this))
                .collect(Collectors.toMap(MetaChartSourceItemPO::getSourceItemId, Function.identity()));
        } else {
            this.whereMap = new HashMap<>();
        }
    }

    /**
     * 装配having条件
     *
     * @param map
     */
    private void assembleHavingList(Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map) {
        List<HavingPO> havingList = (List<HavingPO>) map.get(SourceItemType.HAVING.getValue());
        if (havingList != null) {
            this.havingMap = havingList.stream()
                .peek(item -> item.assembleItem(this))
                .collect(Collectors.toMap(MetaChartSourceItemPO::getSourceItemId, Function.identity()));
        } else {
            this.havingMap = new HashMap<>();
        }
    }

    /**
     * 装配明细排序
     *
     * @param map
     */
    private void assembleDetailOrderList(Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map) {
        List<DetailOrderPO> detailOrderList = (List<DetailOrderPO>) map.get(SourceItemType.DETAIL_ORDER.getValue());
        if (detailOrderList != null) {
            this.detailOrderMap = detailOrderList.stream()
                .peek(item -> item.assembleItem(this))
                .collect(Collectors.toMap(MetaChartSourceItemPO::getSourceItemId, Function.identity()));
        } else {
            this.detailOrderMap = new HashMap<>();
        }
    }

    /**
     * 装配聚合排序
     *
     * @param map
     */
    private void assembleAggOrderList(Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map) {
        List<AggOrderPO> aggOrderList = (List<AggOrderPO>) map.get(SourceItemType.AGG_ORDER.getValue());
        if (aggOrderList != null) {
            this.aggOrderMap = aggOrderList.stream()
                .peek(item -> item.assembleItem(this))
                .collect(Collectors.toMap(MetaChartSourceItemPO::getSourceItemId, Function.identity()));
        } else {
            this.aggOrderMap = new HashMap<>();
        }
    }

    /**
     * 装配实体和joins
     *
     * @param entityMap
     * @param mtmMap
     */
    private void assembleEntityAndJoins(Map<Integer, MetaEntityPO> entityMap,
                                        Map<Integer, MetaManyToManyPO> mtmMap) {
        this.entity = this.forceGetEntityFromMap(entityMap, this.entityId);
        if (CollectionUtils.isEmpty(this.joins)) {
            return;
        }
        for (JoinDTO join : this.joins) {
            if (join.getLeftEntityId() != null) {
                MetaEntityPO entity = this.forceGetEntityFromMap(entityMap, join.getLeftEntityId());
                join.setLeftEntity(entity);
                Integer fieldId = join.getLeftFieldId();
                MetaFieldPO field = entity.getFields().get(fieldId);
                if (field == null) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，字段不存在，fieldId=" + fieldId);
                }
                join.setLeftField(field);
            } else if (join.getLeftMtmId() != null) {
                MetaManyToManyPO mtm = this.forceGetMtmFromMap(mtmMap, join.getLeftMtmId());
                join.setLeftMtm(mtm);
                String mtmField = join.getLeftMtmField();
                if (Objects.equals(mtmField, mtm.getEntityIdField1())
                    && Objects.equals(mtmField, mtm.getEntityIdField2())) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，leftMtmField=" + mtmField + "不存在");
                }
            } else {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，leftEntityId和leftMtmId都为空");
            }
            if (join.getRightEntityId() != null) {
                MetaEntityPO entity = this.forceGetEntityFromMap(entityMap, join.getRightEntityId());
                join.setRightEntity(entity);
                Integer fieldId = join.getRightFieldId();
                MetaFieldPO field = entity.getFields().get(fieldId);
                if (field == null) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，字段不存在，fieldId=" + fieldId);
                }
                join.setRightField(field);
            } else if (join.getRightMtmId() != null) {
                MetaManyToManyPO mtm = this.forceGetMtmFromMap(mtmMap, join.getRightMtmId());
                join.setRightMtm(mtm);
                String mtmField = join.getRightMtmField();
                if (Objects.equals(mtmField, mtm.getEntityIdField1())
                    && Objects.equals(mtmField, mtm.getEntityIdField2())) {
                    throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，rightMtmField=" + mtmField + "不存在");
                }
            } else {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，rightEntityId和rightMtmId都为空");
            }
        }

    }

    /**
     * 从map里面强制获取实体，不存在则抛异常
     *
     * @param entityMap
     * @param entityId
     * @return
     */
    private MetaEntityPO forceGetEntityFromMap(Map<Integer, MetaEntityPO> entityMap, Integer entityId) {
        MetaEntityPO entityPO = entityMap.get(entityId);
        if (entityPO == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，实体不存在，entityId=" + entityId);
        }
        return entityPO;
    }

    /**
     * 从map里面强制获取多对多，不存在则抛异常
     *
     * @param mtmMap
     * @param mtmId
     * @return
     */
    private MetaManyToManyPO forceGetMtmFromMap(Map<Integer, MetaManyToManyPO> mtmMap, Integer mtmId) {
        MetaManyToManyPO mtmPO = mtmMap.get(mtmId);
        if (mtmPO == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，多对多不存在，mtmId=" + mtmId);
        }
        return mtmPO;
    }

    /**
     * 反序列化特性json
     * 从json字符串中解析出dto信息
     */
    public void featureDeserialize() {
        ChartSourceFeatureDTO featureDTO = FeatureMapper.asChartSourceFeatureDTO(this.getFeature());
        this.entityId = featureDTO.getEntityId();
        this.joins = featureDTO.getJoins();
        this.limit = featureDTO.getLimit();
    }

    /**
     * 序列化特性json
     * 将dto信息序列化成json字符串
     */
    public void featureSerialize() {
        ChartSourceFeatureDTO featureDTO = new ChartSourceFeatureDTO();
        featureDTO.setEntityId(this.entityId);
        featureDTO.setJoins(this.joins);
        featureDTO.setLimit(this.limit);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Boolean getAggregation() {
        return this.aggregation;
    }

    public void setAggregation(Boolean aggregation) {
        this.aggregation = aggregation;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public List<JoinDTO> getJoins() {
        return joins;
    }

    public void setJoins(List<JoinDTO> joins) {
        this.joins = joins;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Map<Integer, DetailColumnPO> getDetailColumnMap() {
        return detailColumnMap;
    }

    public void setDetailColumnMap(Map<Integer, DetailColumnPO> detailColumnMap) {
        this.detailColumnMap = detailColumnMap;
    }

    public Map<Integer, DimensionPO> getDimensionMap() {
        return dimensionMap;
    }

    public void setDimensionMap(Map<Integer, DimensionPO> dimensionMap) {
        this.dimensionMap = dimensionMap;
    }

    public Map<Integer, MetricsPO> getMetricsMap() {
        return metricsMap;
    }

    public void setMetricsMap(Map<Integer, MetricsPO> metricsMap) {
        this.metricsMap = metricsMap;
    }

    public Map<Integer, WherePO> getWhereMap() {
        return whereMap;
    }

    public void setWhereMap(Map<Integer, WherePO> whereMap) {
        this.whereMap = whereMap;
    }

    public Map<Integer, HavingPO> getHavingMap() {
        return havingMap;
    }

    public void setHavingMap(Map<Integer, HavingPO> havingMap) {
        this.havingMap = havingMap;
    }

    public Map<Integer, DetailOrderPO> getDetailOrderMap() {
        return detailOrderMap;
    }

    public void setDetailOrderMap(Map<Integer, DetailOrderPO> detailOrderMap) {
        this.detailOrderMap = detailOrderMap;
    }

    public Map<Integer, AggOrderPO> getAggOrderMap() {
        return aggOrderMap;
    }

    public void setAggOrderMap(Map<Integer, AggOrderPO> aggOrderMap) {
        this.aggOrderMap = aggOrderMap;
    }

    public MetaEntityPO getEntity() {
        return entity;
    }

    public void setEntity(MetaEntityPO entity) {
        this.entity = entity;
    }
}

