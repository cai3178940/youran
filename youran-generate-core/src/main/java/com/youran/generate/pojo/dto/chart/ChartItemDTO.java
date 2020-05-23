package com.youran.generate.pojo.dto.chart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.pojo.po.chart.source.item.MetaChartSourceItemPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 图表项DTO
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "图表项DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartItemDTO<T extends MetaChartSourceItemPO> extends AbstractDTO {

    @ApiModelProperty(notes = "数据项ID",example = "1",required = true)
    @NotNull
    private Integer sourceItemId;

    @ApiModelProperty(notes = "字段别名", example = "alias1")
    private String alias;

    @ApiModelProperty(notes = "标题别名",example = "新的列名")
    private String titleAlias;

    @ApiModelProperty(notes = "外键是否用title替换id展示",example = "false")
    private Boolean showFkTitle;

    @ApiModelProperty(notes = "内容前置",example = "前缀_")
    private String valuePrefix;
    @ApiModelProperty(notes = "内容后缀",example = "_后缀")
    private String valueSuffix;

    @ApiModelProperty(notes = "是否百分比",example = "false")
    private Boolean percent;

    // TODO 数据格式化设置-数字、百分比、日期

    /**
     * 数据项
     */
    @JsonIgnore
    private transient T sourceItem;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

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

    public Boolean getShowFkTitle() {
        return showFkTitle;
    }

    public void setShowFkTitle(Boolean showFkTitle) {
        this.showFkTitle = showFkTitle;
    }

    public String getValuePrefix() {
        return valuePrefix;
    }

    public void setValuePrefix(String valuePrefix) {
        this.valuePrefix = valuePrefix;
    }

    public String getValueSuffix() {
        return valueSuffix;
    }

    public void setValueSuffix(String valueSuffix) {
        this.valueSuffix = valueSuffix;
    }

    public T getSourceItem() {
        return sourceItem;
    }

    public void setSourceItem(T sourceItem) {
        this.sourceItem = sourceItem;
    }

    public Boolean getPercent() {
        return percent;
    }

    public void setPercent(Boolean percent) {
        this.percent = percent;
    }
}
