package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.PiePO;

/**
 * 饼图上下文对象
 *
 * @author: cbb
 * @date: 2020-04-12
 */
public class PieContext extends AbstractChartContext<PiePO> {

    /**
     * 维度项
     */
    private ChartItemDTO dimension;

    /**
     * 指标项
     */
    private ChartItemDTO metrics;

    public PieContext(MetaProjectPO project, PiePO pie) {
        super(project, pie);
        this.dimension = pie.getDimension();
        this.metrics = pie.getMetrics();
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
