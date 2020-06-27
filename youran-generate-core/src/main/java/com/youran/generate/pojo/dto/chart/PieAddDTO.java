package com.youran.generate.pojo.dto.chart;

import com.youran.generate.constant.ChartType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增【饼图】入参
 *
 * @author: cbb
 * @date: 2020-04-05
 */
@ApiModel(description = "新增【饼图】入参")
public class PieAddDTO extends AbstractChartDTO {

    @ApiModelProperty(notes = "维度项")
    private ChartItemDTO dimension;

    @ApiModelProperty(notes = "指标项")
    private ChartItemDTO metrics;

    @ApiModelProperty(notes = "图表配置项模板")
    private String optionTemplate;

    @Override
    public Integer getChartType() {
        return ChartType.PIE.getValue();
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

    public String getOptionTemplate() {
        return optionTemplate;
    }

    public void setOptionTemplate(String optionTemplate) {
        this.optionTemplate = optionTemplate;
    }
}
