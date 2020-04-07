package com.youran.generate.pojo.po.chart.source;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.ChartSourceFeatureDTO;
import com.youran.generate.pojo.dto.chart.source.JoinDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import com.youran.generate.pojo.po.BasePO;
import com.youran.generate.pojo.po.chart.source.item.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
     * 明细列
     */
    @JsonIgnore
    private List<DetailColumnPO> detailColumnList;
    /**
     * 维度
     */
    @JsonIgnore
    private List<DimensionPO> dimensionList;
    /**
     * 指标
     */
    @JsonIgnore
    private List<MetricsPO> metricsList;
    /**
     * where条件
     */
    @JsonIgnore
    private List<WherePO> whereList;
    /**
     * having条件
     */
    @JsonIgnore
    private List<HavingPO> havingList;
    /**
     * 明细排序
     */
    @JsonIgnore
    private List<DetailOrderPO> detailOrderList;
    /**
     * 聚合排序
     */
    @JsonIgnore
    private List<AggOrderPO> aggOrderList;

    /**
     * 装配数据项
     *
     * @param items
     */
    public void assembleItems(List<? extends MetaChartSourceItemPO> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map = items.stream()
            .collect(Collectors.groupingBy(e -> e.getType()));
        List<DetailColumnPO> detailColumnList = (List<DetailColumnPO>) map.get(SourceItemType.DETAIL_COLUMN.getValue());
        if (detailColumnList != null) {
            this.detailColumnList =  detailColumnList;
        }else{
            this.detailColumnList =  new ArrayList<>();
        }
        List<DimensionPO> dimensionList = (List<DimensionPO>) map.get(SourceItemType.DIMENSION.getValue());
        if (dimensionList != null) {
            this.dimensionList =  dimensionList;
        }else{
            this.dimensionList =  new ArrayList<>();
        }
        List<MetricsPO> metricsList = (List<MetricsPO>) map.get(SourceItemType.METRICS.getValue());
        if (metricsList != null) {
            this.metricsList =  metricsList;
        }else{
            this.metricsList =  new ArrayList<>();
        }
        List<WherePO> whereList = (List<WherePO>) map.get(SourceItemType.WHERE.getValue());
        if (whereList != null) {
            this.whereList =  whereList;
        }else{
            this.whereList =  new ArrayList<>();
        }
        List<HavingPO> havingList = (List<HavingPO>) map.get(SourceItemType.HAVING.getValue());
        if (havingList != null) {
            this.havingList =  havingList;
        }else{
            this.havingList =  new ArrayList<>();
        }
        List<DetailOrderPO> detailOrderList = (List<DetailOrderPO>) map.get(SourceItemType.DETAIL_ORDER.getValue());
        if (detailOrderList != null) {
            this.detailOrderList =  detailOrderList;
        }else{
            this.detailOrderList =  new ArrayList<>();
        }
        List<AggOrderPO> aggOrderList = (List<AggOrderPO>) map.get(SourceItemType.AGG_ORDER.getValue());
        if (aggOrderList != null) {
            this.aggOrderList =  aggOrderList;
        }else{
            this.aggOrderList =  new ArrayList<>();
        }
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

    public List<DetailColumnPO> getDetailColumnList() {
        return detailColumnList;
    }

    public void setDetailColumnList(List<DetailColumnPO> detailColumnList) {
        this.detailColumnList = detailColumnList;
    }

    public List<DimensionPO> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<DimensionPO> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<MetricsPO> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<MetricsPO> metricsList) {
        this.metricsList = metricsList;
    }

    public List<WherePO> getWhereList() {
        return whereList;
    }

    public void setWhereList(List<WherePO> whereList) {
        this.whereList = whereList;
    }

    public List<HavingPO> getHavingList() {
        return havingList;
    }

    public void setHavingList(List<HavingPO> havingList) {
        this.havingList = havingList;
    }

    public List<DetailOrderPO> getDetailOrderList() {
        return detailOrderList;
    }

    public void setDetailOrderList(List<DetailOrderPO> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    public List<AggOrderPO> getAggOrderList() {
        return aggOrderList;
    }

    public void setAggOrderList(List<AggOrderPO> aggOrderList) {
        this.aggOrderList = aggOrderList;
    }
}

