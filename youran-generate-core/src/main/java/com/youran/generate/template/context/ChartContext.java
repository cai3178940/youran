package com.youran.generate.template.context;

import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.MetaChartPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.util.SwitchCaseUtil;

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
    private String chartName;

    /**
     * 图表名称-首个单词转小写
     */
    private String chartNameLower;

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
        this.chartName = chart.getChartName();
        this.chartNameLower = SwitchCaseUtil.lowerFirstWord(chart.getChartName());
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

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public String getChartNameLower() {
        return chartNameLower;
    }

    public void setChartNameLower(String chartNameLower) {
        this.chartNameLower = chartNameLower;
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
