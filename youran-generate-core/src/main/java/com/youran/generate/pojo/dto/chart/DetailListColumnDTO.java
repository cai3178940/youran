package com.youran.generate.pojo.dto.chart;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 明细列入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "明细列入参")
public class DetailListColumnDTO extends AbstractDTO {

    @ApiModelProperty(notes = "数据项ID",example = "1",required = true)
    @NotNull
    private Integer sourceItemId;

    @ApiModelProperty(notes = "列标题别名",example = "新的列名")
    private String titleAlias;

    @ApiModelProperty(notes = "列格式",example = "yyyy-MM-dd")
    private String format;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
