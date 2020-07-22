package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.MetaChartPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.template.context.BaseContext;
import com.youran.generate.util.SwitchCaseUtil;

/**
 * 抽象图表上下文对象
 *
 * @author: cbb
 * @date: 2020-04-06
 */
public abstract class AbstractChartContext<ChartPO extends MetaChartPO> extends BaseContext {

    /**
     * 图表对象
     */
    private final ChartPO chart;
    /**
     * 主键ID
     */
    private final Integer chartId;

    /**
     * 图表类型
     *
     * @see com.youran.generate.constant.ChartType
     */
    private final Integer chartType;

    /**
     * 图表名称
     */
    private final String chartName;

    /**
     * 图表名称-首个单词转小写
     */
    private final String chartNameLower;

    /**
     * 模块名
     */
    private final String module;

    /**
     * 图表标题
     */
    private final String title;
    /**
     * 图表数据源
     */
    private final MetaChartSourcePO chartSource;


    public AbstractChartContext(MetaProjectPO project, ChartPO chart) {
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

    public MetaChartSourcePO getChartSource() {
        return chartSource;
    }

    public Integer getChartId() {
        return chartId;
    }

    public Integer getChartType() {
        return chartType;
    }

    public String getChartName() {
        return chartName;
    }

    public String getChartNameLower() {
        return chartNameLower;
    }

    public String getModule() {
        return module;
    }

    public String getTitle() {
        return title;
    }
}
