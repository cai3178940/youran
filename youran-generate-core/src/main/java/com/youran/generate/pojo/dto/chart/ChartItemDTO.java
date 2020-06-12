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

    @ApiModelProperty(notes = "内容前缀",example = "前缀_")
    private String valuePrefix;
    @ApiModelProperty(notes = "内容后缀",example = "_后缀")
    private String valueSuffix;

    @ApiModelProperty(notes = "是否百分比",example = "false")
    private Boolean percent;

    @ApiModelProperty(notes = "格式化规则",example = "yyyy-MM-dd")
    private String format;

    @ApiModelProperty(notes = "系列类型",example = "line")
    private String seriesType;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }
}
