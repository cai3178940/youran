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
    private List<ChartItemDTO> columnList;

    /**
     * 维度列
     */
    private List<ChartItemDTO> dimensionList;
    /**
     * 指标列
     */
    private List<ChartItemDTO> metricsList;

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
    private List<ChartItemDTO> axisYList;

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

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public List<ChartItemDTO> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ChartItemDTO> columnList) {
        this.columnList = columnList;
    }

    public List<ChartItemDTO> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<ChartItemDTO> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<ChartItemDTO> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<ChartItemDTO> metricsList) {
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

    public List<ChartItemDTO> getAxisYList() {
        return axisYList;
    }

    public void setAxisYList(List<ChartItemDTO> axisYList) {
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
}
