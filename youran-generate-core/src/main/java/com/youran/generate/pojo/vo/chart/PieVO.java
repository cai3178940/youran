package com.youran.generate.pojo.vo.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;

/**
 * 饼图
 *
 * @author: cbb
 * @date: 2020-05-05
 */
public class PieVO extends MetaChartShowVO {

    /**
     * 维度项
     */
    private ChartItemDTO dimension;

    /**
     * 指标项
     */
    private ChartItemDTO metrics;

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
