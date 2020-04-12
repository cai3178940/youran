package com.youran.generate.pojo.dto.chart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.pojo.dto.AbstractDTO;

import java.util.List;

/**
 * 图表特性
 *
 * @author: cbb
 * @date: 2020-04-05
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartFeatureDTO extends AbstractDTO {

    /**
     * 明细列
     */
    private List<?> columnList;

    /**
     * 维度列
     */
    private List<?> dimensionList;
    /**
     * 指标列
     */
    private List<?> metricsList;

    /**
     * 主X轴
     */
    private ChartItemDTO axisX;
    /**
     * 附加X轴
     */
    private ChartItemDTO axisX2;
    /**
     * Y轴
     */
    private List<?> axisYList;

    /**
     * 维度项
     */
    private ChartItemDTO dimension;
    /**
     * 指标项
     */
    private ChartItemDTO metrics;

    /**
     * 默认每页记录数
     */
    private Integer defaultPageSize;

    public List<?> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<?> columnList) {
        this.columnList = columnList;
    }

    public List<?> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<?> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<?> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<?> metricsList) {
        this.metricsList = metricsList;
    }

    public ChartItemDTO getAxisX() {
        return axisX;
    }

    public void setAxisX(ChartItemDTO axisX) {
        this.axisX = axisX;
    }

    public ChartItemDTO getAxisX2() {
        return axisX2;
    }

    public void setAxisX2(ChartItemDTO axisX2) {
        this.axisX2 = axisX2;
    }

    public List<?> getAxisYList() {
        return axisYList;
    }

    public void setAxisYList(List<?> axisYList) {
        this.axisYList = axisYList;
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

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}
