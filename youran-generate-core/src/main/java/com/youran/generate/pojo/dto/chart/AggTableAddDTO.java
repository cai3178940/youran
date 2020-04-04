package com.youran.generate.pojo.dto.chart;

import com.youran.generate.constant.ChartType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 新增【聚合表】入参
 *
 * @author: cbb
 * @date: 2020-04-05
 */
@ApiModel(description = "新增【聚合表】入参")
public class AggTableAddDTO extends AbstractChartDTO {

    @ApiModelProperty(notes = "维度列")
    private List<AggTableColumnDTO> dimensions;

    @ApiModelProperty(notes = "指标列")
    private List<AggTableColumnDTO> metrics;

    // TODO 以后再扩展其他样式

    @Override
    public Integer getChartType() {
        return ChartType.AGG_TABLE.getValue();
    }

    public List<AggTableColumnDTO> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<AggTableColumnDTO> dimensions) {
        this.dimensions = dimensions;
    }

    public List<AggTableColumnDTO> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<AggTableColumnDTO> metrics) {
        this.metrics = metrics;
    }
}
