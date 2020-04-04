package com.youran.generate.pojo.dto.chart.sourceitem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 修改【where条件】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "修改【where条件】入参")
public class WhereUpdateDTO extends WhereAddDTO {

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
