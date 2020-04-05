package com.youran.generate.pojo.po.chart;

import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

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
