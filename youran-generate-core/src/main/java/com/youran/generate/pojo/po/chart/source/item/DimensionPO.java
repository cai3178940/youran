package com.youran.generate.pojo.po.chart.source.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;

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

    /**
     * 对应实体
     */
    @JsonIgnore
    private transient MetaEntityPO entity;
    /**
     * 对应字段
     */
    @JsonIgnore
    private transient MetaFieldPO field;

    public DimensionPO() {
        this.setType(SourceItemType.DIMENSION.getValue());
    }


    @Override
    public void assembleItem(MetaChartSourcePO chartSource) {
        super.assembleItem(chartSource);
        if (this.getJoinIndex() == 0) {
            this.entity = chartSource.getEntity();
        } else {
            this.entity = this.getJoin().getRightEntity();
        }
        if(this.entity==null){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表维度异常，所属实体不存在");
        }
        this.field = this.entity.getFields().get(this.fieldId);
        if(this.field==null){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表维度异常，所属字段不存在");
        }
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

    public MetaEntityPO getEntity() {
        return entity;
    }

    public void setEntity(MetaEntityPO entity) {
        this.entity = entity;
    }

    public MetaFieldPO getField() {
        return field;
    }

    public void setField(MetaFieldPO field) {
        this.field = field;
    }
}
