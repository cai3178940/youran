package com.youran.generate.pojo.po.chart.source.item;

import com.youran.generate.constant.SourceItemSubType;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

/**
 * 指标
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class MetricsPO extends MetaChartSourceItemPO {


    /**
     * 字段id
     */
    private Integer fieldId;

    /**
     * 别名
     */
    private String alias;

    /**
     * 聚合函数
     */
    private Integer aggFunction;

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

    public MetricsPO() {
        this.setType(SourceItemType.METRICS.getValue());
        this.setSubType(SourceItemSubType.NONE.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartSourceItemFeatureDTO featureDTO = FeatureMapper.asChartSourceItemFeatureDTO(this.getFeature());
        this.fieldId = featureDTO.getFieldId();
        this.alias = featureDTO.getAlias();
        this.aggFunction = featureDTO.getAggFunction();
        this.custom = featureDTO.getCustom();
        this.customContent = featureDTO.getCustomContent();
        this.customFieldType = featureDTO.getCustomFieldType();
    }

    @Override
    public void featureSerialize() {
        ChartSourceItemFeatureDTO featureDTO = new ChartSourceItemFeatureDTO();
        featureDTO.setFieldId(this.fieldId);
        featureDTO.setAlias(this.alias);
        featureDTO.setAggFunction(this.aggFunction);
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

    public Integer getAggFunction() {
        return aggFunction;
    }

    public void setAggFunction(Integer aggFunction) {
        this.aggFunction = aggFunction;
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
