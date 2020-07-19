package com.youran.generate.pojo.po.chart.source.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
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
    @JsonIgnore
    private Integer fieldId;

    /**
     * 维度粒度
     */
    @JsonIgnore
    private Integer granularity;

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
            this.entity = this.getJoin().getRight().getEntity();
        }
        if(this.entity==null){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表维度异常，所属实体不存在");
        }
        this.field = this.entity.getFields().get(this.fieldId);
        if(this.field==null){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表维度异常，所属字段不存在");
        }
    }

    /**
     * 将超类转成当前类型
     *
     * @param superPO
     * @param featureDeserialize
     * @return
     */
    public static DimensionPO fromSuperType(MetaChartSourceItemPO superPO,
                                            boolean featureDeserialize) {
        if (!SourceItemType.DIMENSION.getValue().equals(superPO.getType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        DimensionPO po = new DimensionPO();
        MetaChartSourceItemMapper.INSTANCE.copyProperties(po, superPO);
        if (featureDeserialize) {
            po.featureDeserialize();
        }
        return po;
    }

    @Override
    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.fieldId = featureDTO.getFieldId();
        this.granularity = featureDTO.getGranularity();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setFieldId(this.fieldId);
        featureDTO.setGranularity(this.granularity);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }


    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getGranularity() {
        return granularity;
    }

    public void setGranularity(Integer granularity) {
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

    static class FeatureDTO{
        private Integer fieldId;
        private Integer granularity;

        public Integer getFieldId() {
            return fieldId;
        }

        public void setFieldId(Integer fieldId) {
            this.fieldId = fieldId;
        }

        public Integer getGranularity() {
            return granularity;
        }

        public void setGranularity(Integer granularity) {
            this.granularity = granularity;
        }
    }
}
