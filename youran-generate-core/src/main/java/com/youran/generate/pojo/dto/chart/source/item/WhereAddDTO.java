package com.youran.generate.pojo.dto.chart.source.item;

import com.youran.common.validator.Const;
import com.youran.generate.constant.FilterOperator;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.constant.TimeGranularity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 新增【where条件】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "新增【where条件】入参")
public class WhereAddDTO extends AbstractSourceItemDTO {

    @ApiModelProperty(notes = "字段id", example = "1")
    private Integer fieldId;

    @ApiModelProperty(notes = "过滤运算符", example = "1", allowableValues = FilterOperator.VALUES_STR)
    @Const(constClass = FilterOperator.class)
    private Integer filterOperator;

    @ApiModelProperty(notes = "过滤值")
    private String[] filterValue;

    @ApiModelProperty(notes = "时间粒度", example = "1", allowableValues = TimeGranularity.VALUES_STR)
    @Const(constClass = TimeGranularity.class)
    private Integer timeGranularity;

    @ApiModelProperty(notes = "是否自定义", example = "true", required = true)
    @NotNull
    private Boolean custom;

    @ApiModelProperty(notes = "自定义内容")
    private String customContent;


    @Override
    public Integer getType() {
        return SourceItemType.WHERE.getValue();
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(Integer filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String[] getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String[] filterValue) {
        this.filterValue = filterValue;
    }

    public Integer getTimeGranularity() {
        return timeGranularity;
    }

    public void setTimeGranularity(Integer timeGranularity) {
        this.timeGranularity = timeGranularity;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent;
    }
}
