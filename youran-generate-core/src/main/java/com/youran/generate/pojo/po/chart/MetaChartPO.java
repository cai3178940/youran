package com.youran.generate.pojo.po.chart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.po.BasePO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;

import java.util.Map;

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


    public MetaChartPO() {}

    /**
     * 装配数据
     *
     * @param chartSource
     */
    public void assemble(MetaChartSourcePO chartSource) {
        this.chartSource = chartSource;
    }

    /**
     * 校验数据是否有误
     * @param entityMap
     * @param mtmMap
     */
    public void check(Map<Integer, MetaEntityPO> entityMap, Map<Integer, MetaManyToManyPO> mtmMap) {
        if(chartSource == null){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表【" + this.chartName +
                "】无数据源");
        }
    }

    /**
     * 转换成子类
     *
     * @param featureDeserialize
     * @param <T>
     * @return
     */
    public <T extends MetaChartPO> T castSubType(boolean featureDeserialize) {
        if (ChartType.AGG_TABLE.getValue().equals(this.chartType)) {
            return (T) AggTablePO.fromSuperType(this, featureDeserialize);
        } else if (ChartType.DETAIL_LIST.getValue().equals(this.chartType)) {
            return (T) DetailListPO.fromSuperType(this, featureDeserialize);
        } else if (ChartType.BAR_LINE.getValue().equals(this.chartType)) {
            return (T) BarLinePO.fromSuperType(this, featureDeserialize);
        } else if (ChartType.PIE.getValue().equals(this.chartType)) {
            return (T) PiePO.fromSuperType(this, featureDeserialize);
        }
        throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
    }

    /**
     * 反序列化特性json
     * 从json字符串中解析出dto信息
     */
    public void featureDeserialize() {
        throw new RuntimeException("未实现");
    }

    /**
     * 序列化特性json
     * 将dto信息序列化成json字符串
     */
    public void featureSerialize() {
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

