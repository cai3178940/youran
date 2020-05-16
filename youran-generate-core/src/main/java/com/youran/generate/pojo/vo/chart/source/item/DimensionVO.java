package com.youran.generate.pojo.vo.chart.source.item;

/**
 * 维度
 *
 * @author: cbb
 * @date: 2020-05-05
 */
public class DimensionVO extends MetaChartSourceItemVO {

    /**
     * 字段id
     */
    private Integer fieldId;

    /**
     * 维度粒度
     */
    private String granularity;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }
}
