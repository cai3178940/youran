package com.youran.generate.pojo.po.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
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
    private List<ChartItemDTO> dimensionList;

    /**
     * 指标列
     */
    private List<ChartItemDTO> metricsList;

    public AggTablePO() {
        this.setChartType(ChartType.AGG_TABLE.getValue());
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
        ChartFeatureDTO featureDTO = FeatureMapper.asChartFeatureDTO(this.getFeature());
        this.dimensionList = featureDTO.getDimensionList();
        this.metricsList = featureDTO.getMetricsList();
    }

    @Override
    public void featureSerialize() {
        ChartFeatureDTO featureDTO = new ChartFeatureDTO();
        featureDTO.setDimensionList(this.dimensionList);
        featureDTO.setMetricsList(this.metricsList);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public List<ChartItemDTO> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<ChartItemDTO> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<ChartItemDTO> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<ChartItemDTO> metricsList) {
        this.metricsList = metricsList;
    }
}
