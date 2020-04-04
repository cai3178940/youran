package com.youran.generate.pojo.dto.chart.source;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 修改【图表数据源】的参数
 *
 * @author cbb
 * @date 2020/04/04
 */
@ApiModel(description = "修改【图表数据源】的参数")
public class MetaChartSourceUpdateDTO extends MetaChartSourceAddDTO {

    @ApiModelProperty(notes = "主键",example = "1",required = true)
    @NotNull
    private Integer sourceId;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}


