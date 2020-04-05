package com.youran.generate.pojo.po.chart.source.item;

import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

/**
 * where条件
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class WherePO extends MetaChartSourceItemPO {

    /**
     * 字段id
     */
    private Integer fieldId;

    /**
     * 过滤运算符
     */
    private Integer filterOperator;

    /**
     * 过滤值
     */
    private String[] filterValue;

    /**
     * 是否自定义
     */
    private Boolean custom;

    /**
     * 自定义内容
     */
    private String customContent;

    public WherePO() {
        this.setType(SourceItemType.WHERE.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartSourceItemFeatureDTO featureDTO = FeatureMapper.asChartSourceItemFeatureDTO(this.getFeature());
        this.fieldId = featureDTO.getFieldId();
        this.filterOperator = featureDTO.getFilterOperator();
        this.filterValue = featureDTO.getFilterValue();
        this.custom = featureDTO.getCustom();
        this.customContent = featureDTO.getCustomContent();
    }

    @Override
    public void featureSerialize() {
        ChartSourceItemFeatureDTO featureDTO = new ChartSourceItemFeatureDTO();
        featureDTO.setFieldId(this.fieldId);
        featureDTO.setFilterOperator(this.filterOperator);
        featureDTO.setFilterValue(this.filterValue);
        featureDTO.setCustom(this.custom);
        featureDTO.setCustomContent(this.customContent);
        this.setFeature(FeatureMapper.asString(featureDTO));
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
