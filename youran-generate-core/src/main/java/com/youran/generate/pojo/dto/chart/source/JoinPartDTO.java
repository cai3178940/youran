package com.youran.generate.pojo.dto.chart.source;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.validator.Const;
import com.youran.generate.constant.JoinPartType;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.util.AssembleUtil;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

/**
 * 关联部分
 *
 * @author: cbb
 * @date: 2020-05-17
 */
public class JoinPartDTO {

    @ApiModelProperty(notes = "类型", example = "1")
    @NotNull
    @Const(constClass = JoinPartType.class)
    private String joinPartType;

    @ApiModelProperty(notes = "关联序号", example = "1")
    private Integer joinIndex;

    @ApiModelProperty(notes = "实体id", example = "1")
    private Integer entityId;

    @ApiModelProperty(notes = "左字段id", example = "1")
    private Integer fieldId;

    @ApiModelProperty(notes = "左多对多id", example = "1")
    private Integer mtmId;

    @ApiModelProperty(notes = "左多对多字段", example = "1")
    private String mtmField;

    /**
     * 左实体
     */
    @JsonIgnore
    private transient MetaEntityPO entity;
    /**
     * 左字段
     */
    @JsonIgnore
    private transient MetaFieldPO field;
    /**
     * 左多对多
     */
    @JsonIgnore
    private transient MetaManyToManyPO mtm;


    /**
     * 装配对象
     *
     * @param entityMap
     * @param mtmMap
     */
    public void assemble(Map<Integer, MetaEntityPO> entityMap,
                         Map<Integer, MetaManyToManyPO> mtmMap) {
        if (JoinPartType.ENTITY.equals(joinPartType)) {
            this.entity = AssembleUtil.forceGetEntityFromMap(entityMap, this.entityId);
            this.field = this.entity.getFields().get(this.fieldId);
            if (this.field == null) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，字段不存在，fieldId=" + this.fieldId);
            }
        } else if (JoinPartType.MTM.equals(joinPartType)) {
            this.mtm = AssembleUtil.forceGetMtmFromMap(mtmMap, this.mtmId);
            if (Objects.equals(this.mtmField, mtm.getEntityIdField1())
                && Objects.equals(this.mtmField, mtm.getEntityIdField2())) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，leftMtmField=" + mtmField + "不存在");
            }
        } else {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，leftEntityId和leftMtmId都为空");
        }
    }

    public String getJoinPartType() {
        return joinPartType;
    }

    public void setJoinPartType(String joinPartType) {
        this.joinPartType = joinPartType;
    }

    public Integer getJoinIndex() {
        return joinIndex;
    }

    public void setJoinIndex(Integer joinIndex) {
        this.joinIndex = joinIndex;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getMtmId() {
        return mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public String getMtmField() {
        return mtmField;
    }

    public void setMtmField(String mtmField) {
        this.mtmField = mtmField;
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

    public MetaManyToManyPO getMtm() {
        return mtm;
    }

    public void setMtm(MetaManyToManyPO mtm) {
        this.mtm = mtm;
    }
}
