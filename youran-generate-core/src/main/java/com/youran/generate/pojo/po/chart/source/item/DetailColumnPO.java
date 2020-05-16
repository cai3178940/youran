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
     * 对应实体
     */
    @JsonIgnore
    private transient MetaEntityPO entity;
    /**
     * 对应字段
     */
    @JsonIgnore
    private transient MetaFieldPO field;


    public DetailColumnPO() {
        this.setType(SourceItemType.DETAIL_COLUMN.getValue());
    }


    @Override
    public void assembleItem(MetaChartSourcePO chartSource) {
        super.assembleItem(chartSource);
        // 非自定义的情况下，需要装配实体和字段对象
        if(!custom) {
            if (this.getJoinIndex() == 0) {
                this.entity = chartSource.getEntity();
            } else {
                this.entity = this.getJoin().getRightEntity();
            }
            if (this.entity == null) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表明细列异常，所属实体不存在");
            }
            this.field = this.entity.getFields().get(this.fieldId);
            if (this.field == null) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表明细列异常，所属字段不存在");
            }
        }
    }


    /**
     * 将超类转成当前类型
     *
     * @param superPO
     * @param featureDeserialize
     * @return
     */
    public static DetailColumnPO fromSuperType(MetaChartSourceItemPO superPO,
                                               boolean featureDeserialize) {
        if (!SourceItemType.DETAIL_COLUMN.getValue().equals(superPO.getType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        DetailColumnPO po = new DetailColumnPO();
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
        this.custom = featureDTO.getCustom();
        this.customContent = featureDTO.getCustomContent();
        this.customFieldType = featureDTO.getCustomFieldType();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setFieldId(this.fieldId);
        featureDTO.setCustom(this.custom);
        featureDTO.setCustomContent(this.customContent);
        featureDTO.setCustomFieldType(this.customFieldType);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }

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
        private Boolean custom;
        private String customContent;
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
}
