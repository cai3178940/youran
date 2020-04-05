package com.youran.generate.pojo.po.chart.source.item;

import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

/**
 * 明细列
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class DetailColumnPO extends MetaChartSourceItemPO {

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

    public DetailColumnPO() {
        this.setType(SourceItemType.DETAIL_COLUMN.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartSourceItemFeatureDTO featureDTO = FeatureMapper.asChartSourceItemFeatureDTO(this.getFeature());
        this.fieldId = featureDTO.getFieldId();
        this.alias = featureDTO.getAlias();
        this.custom = featureDTO.getCustom();
        this.customContent = featureDTO.getCustomContent();
        this.customFieldType = featureDTO.getCustomFieldType();
    }

    @Override
    public void featureSerialize() {
        ChartSourceItemFeatureDTO featureDTO = new ChartSourceItemFeatureDTO();
        featureDTO.setFieldId(this.fieldId);
        featureDTO.setAlias(this.alias);
        featureDTO.setCustom(this.custom);
        featureDTO.setCustomContent(this.customContent);
        featureDTO.setCustomFieldType(this.customFieldType);
        this.setFeature(FeatureMapper.asString(featureDTO));
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
}
