package com.youran.generate.template.context;

import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.MetaChartPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;

/**
 * 图表上下文对象
 *
 * @author: cbb
 * @date: 2020-04-06
 */
public class ChartContext<ChartPO extends MetaChartPO> extends BaseContext {

    /**
     * 图表对象
     */
    private ChartPO chart;
    /**
     * 主键ID
     */
    private Integer chartId;

    /**
     * 图表类型
     *
     * @see com.youran.generate.constant.ChartType
     */
    private Integer chartType;

    /**
     * 图表名称
     */
    private String name;

    /**
     * 模块名
     */
    private String module;

    /**
     * 图表标题
     */
    private String title;
    /**
     * 图表数据源
     */
    private MetaChartSourcePO chartSource;


    public ChartContext(MetaProjectPO project, ChartPO chart) {
        super(project);
        this.chart = chart;
        this.chartId = chart.getChartId();
        this.chartType = chart.getChartType();
        this.name = chart.getName();
        this.module = chart.getModule();
        this.title = chart.getTitle();
        this.chartSource = chart.getChartSource();
    }

    public ChartPO getChart() {
        return chart;
    }

    public void setChart(ChartPO chart) {
        this.chart = chart;
    }

    public MetaChartSourcePO getChartSource() {
        return chartSource;
    }

    public void setChartSource(MetaChartSourcePO chartSource) {
        this.chartSource = chartSource;
    }

    public Integer getChartId() {
        return chartId;
    }

    public void setChartId(Integer chartId) {
        this.chartId = chartId;
    }

    public Integer getChartType() {
        return chartType;
    }

    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
