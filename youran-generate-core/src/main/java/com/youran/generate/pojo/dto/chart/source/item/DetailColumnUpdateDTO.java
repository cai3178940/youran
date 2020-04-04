package com.youran.generate.pojo.dto.chart.source.item;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 修改【明细列】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "修改【明细列】入参")
public class DetailColumnUpdateDTO extends DetailColumnAddDTO {

    @ApiModelProperty(notes = "主键ID",example = "1",required = true)
    @NotNull
    private Integer sourceItemId;

    public Integer getSourceItemId() {
        return sourceItemId;
    }

    public void setSourceItemId(Integer sourceItemId) {
        this.sourceItemId = sourceItemId;
    }
}
