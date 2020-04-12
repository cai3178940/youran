package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.AggTablePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;

import java.util.List;

/**
 * 聚合表上下文对象
 *
 * @author: cbb
 * @date: 2020-04-12
 */
public class AggTableContext extends AbstractChartContext<AggTablePO> {

    /**
     * 维度列
     */
    private List<ChartItemDTO<DimensionPO>> dimensionList;

    /**
     * 指标列
     */
    private List<ChartItemDTO<MetricsPO>> metricsList;

    public AggTableContext(MetaProjectPO project, AggTablePO aggTable) {
        super(project, aggTable);
        this.dimensionList = aggTable.getDimensionList();
        this.metricsList = aggTable.getMetricsList();
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
