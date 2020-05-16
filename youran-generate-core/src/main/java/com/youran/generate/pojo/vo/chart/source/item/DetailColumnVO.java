package com.youran.generate.pojo.vo.chart.source.item;

/**
 * 明细列
 *
 * @author: cbb
 * @date: 2020-05-05
 */
public class DetailColumnVO extends MetaChartSourceItemVO {

    /**
     * 字段id
     */
    private Integer fieldId;

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

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
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
