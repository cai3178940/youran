package com.youran.generate.pojo.po.chart;

import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

/**
 * 饼图
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class PiePO extends MetaChartPO {

    /**
     * 维度项
     */
    private ChartItemDTO dimension;

    /**
     * 指标项
     */
    private ChartItemDTO metrics;

    public PiePO() {
        this.setChartType(ChartType.PIE.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartFeatureDTO featureDTO = FeatureMapper.asChartFeatureDTO(this.getFeature());
        this.dimension = featureDTO.getDimension();
        this.metrics = featureDTO.getMetrics();
    }

    @Override
    public void featureSerialize() {
        ChartFeatureDTO featureDTO = new ChartFeatureDTO();
        featureDTO.setDimension(this.dimension);
        featureDTO.setMetrics(this.metrics);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public ChartItemDTO getDimension() {
        return dimension;
    }

    public void setDimension(ChartItemDTO dimension) {
        this.dimension = dimension;
    }

    public ChartItemDTO getMetrics() {
        return metrics;
    }

    public void setMetrics(ChartItemDTO metrics) {
        this.metrics = metrics;
    }
}
