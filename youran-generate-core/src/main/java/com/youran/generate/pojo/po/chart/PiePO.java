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
    private ChartItemDTO<DimensionPO> dimension;

    /**
     * 指标项
     */
    private ChartItemDTO<MetricsPO> metrics;

    public PiePO() {
        this.setChartType(ChartType.PIE.getValue());
    }


    @Override
    public void assemble(MetaChartSourcePO chartSource) {
        super.assemble(chartSource);
        Integer dimensionSourceItemId = dimension.getSourceItemId();
        DimensionPO dimension = chartSource.getDimensionMap().get(dimensionSourceItemId);
        if (dimension != null) {
            this.dimension.setSourceItem(dimension);
        } else {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                "图表【" + this.getTitle() + "】维度项不存在，sourceItemId=" + dimensionSourceItemId);
        }
        Integer metricsSourceItemId = metrics.getSourceItemId();
        MetricsPO metrics = chartSource.getMetricsMap().get(metricsSourceItemId);
        if (metrics != null) {
            this.metrics.setSourceItem(metrics);
        } else {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                "图表【" + this.getTitle() + "】指标项不存在，sourceItemId=" + metricsSourceItemId);
        }
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

    public ChartItemDTO<DimensionPO> getDimension() {
        return dimension;
    }

    public void setDimension(ChartItemDTO<DimensionPO> dimension) {
        this.dimension = dimension;
    }

    public ChartItemDTO<MetricsPO> getMetrics() {
        return metrics;
    }

    public void setMetrics(ChartItemDTO<MetricsPO> metrics) {
        this.metrics = metrics;
    }
}
