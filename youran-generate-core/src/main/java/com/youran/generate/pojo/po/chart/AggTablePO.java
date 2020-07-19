package com.youran.generate.pojo.po.chart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;

import java.util.List;

/**
 * 聚合表
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class AggTablePO extends MetaChartPO {

    /**
     * 维度列
     */
    @JsonIgnore
    private List<ChartItemDTO<DimensionPO>> dimensionList;

    /**
     * 指标列
     */
    @JsonIgnore
    private List<ChartItemDTO<MetricsPO>> metricsList;

    /**
     * 默认每页记录数
     */
    @JsonIgnore
    private Integer defaultPageSize;

    public AggTablePO() {
        this.setChartType(ChartType.AGG_TABLE.getValue());
    }

    /**
     * 将超类转成当前类型
     *
     * @param superPO
     * @param featureDeserialize
     * @return
     */
    public static AggTablePO fromSuperType(MetaChartPO superPO,
                                           boolean featureDeserialize) {
        if (!ChartType.AGG_TABLE.getValue().equals(superPO.getChartType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        AggTablePO po = new AggTablePO();
        MetaChartMapper.INSTANCE.copyProperties(po, superPO);
        if (featureDeserialize) {
            po.featureDeserialize();
        }
        return po;
    }


    @Override
    public void assemble(MetaChartSourcePO chartSource) {
        super.assemble(chartSource);
        for (ChartItemDTO chartItem : dimensionList) {
            Integer sourceItemId = chartItem.getSourceItemId();
            DimensionPO dimension = chartSource.getDimensionMap().get(sourceItemId);
            if (dimension != null) {
                chartItem.setSourceItem(dimension);
            } else {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "图表【" + this.getTitle() + "】维度列不存在，sourceItemId=" + sourceItemId);
            }
        }
        for (ChartItemDTO chartItem : metricsList) {
            Integer sourceItemId = chartItem.getSourceItemId();
            MetricsPO metrics = chartSource.getMetricsMap().get(sourceItemId);
            if (metrics != null) {
                chartItem.setSourceItem(metrics);
            } else {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "图表【" + this.getTitle() + "】指标列不存在，sourceItemId=" + sourceItemId);
            }
        }
    }


    @Override
    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.dimensionList = featureDTO.getDimensionList();
        this.metricsList = featureDTO.getMetricsList();
        this.defaultPageSize = featureDTO.getDefaultPageSize();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setDimensionList(this.dimensionList);
        featureDTO.setMetricsList(this.metricsList);
        featureDTO.setDefaultPageSize(this.defaultPageSize);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }

    public List<ChartItemDTO<DimensionPO>> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<ChartItemDTO<DimensionPO>> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<ChartItemDTO<MetricsPO>> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<ChartItemDTO<MetricsPO>> metricsList) {
        this.metricsList = metricsList;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    static class FeatureDTO{
        private List<ChartItemDTO<DimensionPO>> dimensionList;
        private List<ChartItemDTO<MetricsPO>> metricsList;
        private Integer defaultPageSize;

        public Integer getDefaultPageSize() {
            return defaultPageSize;
        }

        public void setDefaultPageSize(Integer defaultPageSize) {
            this.defaultPageSize = defaultPageSize;
        }

        public List<ChartItemDTO<DimensionPO>> getDimensionList() {
            return dimensionList;
        }

        public void setDimensionList(List<ChartItemDTO<DimensionPO>> dimensionList) {
            this.dimensionList = dimensionList;
        }

        public List<ChartItemDTO<MetricsPO>> getMetricsList() {
            return metricsList;
        }

        public void setMetricsList(List<ChartItemDTO<MetricsPO>> metricsList) {
            this.metricsList = metricsList;
        }
    }

}
