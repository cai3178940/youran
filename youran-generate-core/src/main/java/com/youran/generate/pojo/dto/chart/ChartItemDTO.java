package com.youran.generate.pojo.dto.chart;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 图表项入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "图表项入参")
public class ChartItemDTO extends AbstractDTO {

    @ApiModelProperty(notes = "数据项ID",example = "1",required = true)
    @NotNull
    private Integer sourceItemId;

    @ApiModelProperty(notes = "标题别名",example = "新的列名")
    private String titleAlias;


    public Integer getSourceItemId() {
        return sourceItemId;
    }

    public void setSourceItemId(Integer sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    public String getTitleAlias() {
        return titleAlias;
    }

    public void setTitleAlias(String titleAlias) {
        this.titleAlias = titleAlias;
    }

}
