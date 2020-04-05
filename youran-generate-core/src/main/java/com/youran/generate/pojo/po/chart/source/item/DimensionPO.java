package com.youran.generate.pojo.po.chart.source.item;

import com.youran.generate.constant.SourceItemSubType;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

/**
 * 维度
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class DimensionPO extends MetaChartSourceItemPO {

    /**
     * 字段id
     */
    private Integer fieldId;

    /**
     * 别名
     */
    private String alias;
    /**
     * 维度粒度
     */
    private String granularity;

    public DimensionPO() {
        this.setType(SourceItemType.DIMENSION.getValue());
        this.setSubType(SourceItemSubType.NONE.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartSourceItemFeatureDTO featureDTO = FeatureMapper.asChartSourceItemFeatureDTO(this.getFeature());
        this.fieldId = featureDTO.getFieldId();
        this.alias = featureDTO.getAlias();
        this.granularity = featureDTO.getGranularity();
    }

    @Override
    public void featureSerialize() {
        ChartSourceItemFeatureDTO featureDTO = new ChartSourceItemFeatureDTO();
        featureDTO.setFieldId(this.fieldId);
        featureDTO.setAlias(this.alias);
        featureDTO.setGranularity(this.granularity);
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

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }
}
