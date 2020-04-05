package com.youran.generate.pojo.dto.chart.source.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.pojo.dto.AbstractDTO;

/**
 * 图表数据项特性
 *
 * @author: cbb
 * @date: 2020-04-05
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartSourceItemFeatureDTO extends AbstractDTO {

    /**
     * 字段id
     */
    private Integer fieldId;

    /**
     * 别名
     */
    private String alias;

    /**
     * 是否自定义
     */
    private Boolean custom;

    /**
     * 自定义内容
     */
    private String customContent;

    /**
     * 自定义字段类型
     */
    private Integer customFieldType;


    /**
     * 数据项子类型
     */
    private Integer subType;

    /**
     * 维度粒度
     */
    private String granularity;


    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 排序方式
     */
    private Integer sortType;

    /**
     * 过滤运算符
     */
    private Integer filterOperator;

    /**
     * 过滤值
     */
    private String[] filterValue;

    /**
     * 聚合函数
     */
    private Integer aggFunction;


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

    public Integer getCustomFieldType() {
        return customFieldType;
    }

    public void setCustomFieldType(Integer customFieldType) {
        this.customFieldType = customFieldType;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
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

    public Integer getAggFunction() {
        return aggFunction;
    }

    public void setAggFunction(Integer aggFunction) {
        this.aggFunction = aggFunction;
    }
}
