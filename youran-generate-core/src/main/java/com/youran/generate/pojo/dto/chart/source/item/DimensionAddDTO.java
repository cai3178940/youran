package com.youran.generate.pojo.dto.chart.source.item;

import com.youran.common.validator.Const;
import com.youran.generate.constant.Granularity;
import com.youran.generate.constant.SourceItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 新增【维度】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "新增【维度】入参")
public class DimensionAddDTO extends AbstractSourceItemDTO {

    @ApiModelProperty(notes = "字段id", example = "1", required = true)
    @NotNull
    private Integer fieldId;

    @ApiModelProperty(notes = "别名", example = "alias1")
    private String alias;

    @ApiModelProperty(notes = "维度粒度", example = "1", required = true, allowableValues = Granularity.VALUES_STR)
    @NotNull
    @Const(constClass = Granularity.class)
    private String granularity;

    @Override
    public Integer getType() {
        return SourceItemType.DIMENSION.getValue();
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }
}
