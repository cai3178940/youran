package com.youran.generate.pojo.po.chart;

import com.youran.generate.pojo.po.BasePO;

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
     * 特性json
     */
    private String feature;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

}

