package com.youran.generate.pojo.po.chart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.generate.pojo.po.BasePO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;

/**
 * 图表
 * <p>图表
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartPO extends BasePO {

    /**
     * 主键ID
     */
    private Integer chartId;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 数据源id
     */
    private Integer sourceId;

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
     * 模块名
     */
    private String module;

    /**
     * 图表标题
     */
    private String title;

    /**
     * 特性json
     */
    private String feature;

    /**
     * 图表数据源
     */
    @JsonIgnore
    private transient MetaChartSourcePO chartSource;

    /**
     * 反序列化特性json
     * 从json字符串中解析出dto信息
     */
    public void featureDeserialize(){
        throw new RuntimeException("未实现");
    }

    /**
     * 序列化特性json
     * 将dto信息序列化成json字符串
     */
    public void featureSerialize(){
        throw new RuntimeException("未实现");
    }

    public Integer getChartId() {
        return this.chartId;
    }

    public void setChartId(Integer chartId) {
        this.chartId = chartId;
    }

    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getChartType() {
        return this.chartType;
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

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public MetaChartSourcePO getChartSource() {
        return chartSource;
    }

    public void setChartSource(MetaChartSourcePO chartSource) {
        this.chartSource = chartSource;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}

