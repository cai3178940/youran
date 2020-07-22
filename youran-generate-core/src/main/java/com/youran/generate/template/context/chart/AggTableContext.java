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
    private final List<ChartItemDTO<DimensionPO>> dimensionList;
    /**
     * 指标列
     */
    private final List<ChartItemDTO<MetricsPO>> metricsList;
    /**
     * 默认每页记录数
     */
    private final Integer defaultPageSize;
    /**
     * 是否支持excel导出
     */
    private final Boolean excelExport;

    public AggTableContext(MetaProjectPO project, AggTablePO aggTable) {
        super(project, aggTable);
        this.dimensionList = aggTable.getDimensionList();
        this.metricsList = aggTable.getMetricsList();
        this.defaultPageSize = aggTable.getDefaultPageSize();
        this.excelExport = aggTable.getExcelExport();
    }

    public List<ChartItemDTO<DimensionPO>> getDimensionList() {
        return dimensionList;
    }

    public List<ChartItemDTO<MetricsPO>> getMetricsList() {
        return metricsList;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public Boolean getExcelExport() {
        return excelExport;
    }

}
