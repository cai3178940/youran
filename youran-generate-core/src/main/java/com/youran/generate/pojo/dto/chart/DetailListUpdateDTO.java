package com.youran.generate.pojo.dto.chart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 修改【明细表】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "修改【明细表】入参")
public class DetailListUpdateDTO extends DetailListAddDTO {

    @ApiModelProperty(notes = "图表主键",example = "1",required = true)
    @NotNull
    private Integer chartId;

    public Integer getChartId() {
        return chartId;
    }

    public void setChartId(Integer chartId) {
        this.chartId = chartId;
    }
}
