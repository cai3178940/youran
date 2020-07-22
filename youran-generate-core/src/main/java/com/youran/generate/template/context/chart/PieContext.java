package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.PiePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;

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
    private final ChartItemDTO<DimensionPO> dimension;

    /**
     * 指标项
     */
    private final ChartItemDTO<MetricsPO> metrics;

    /**
     * 图表配置项模板
     */
    private final String optionTemplate;

    public PieContext(MetaProjectPO project, PiePO pie) {
        super(project, pie);
        this.dimension = pie.getDimension();
        this.metrics = pie.getMetrics();
        this.optionTemplate = pie.getOptionTemplate();
    }

    public ChartItemDTO<DimensionPO> getDimension() {
        return dimension;
    }

    public ChartItemDTO<MetricsPO> getMetrics() {
        return metrics;
    }

    public String getOptionTemplate() {
        return optionTemplate;
    }

}
