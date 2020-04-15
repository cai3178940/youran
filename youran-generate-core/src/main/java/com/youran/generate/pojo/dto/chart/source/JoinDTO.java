package com.youran.generate.pojo.dto.chart.source;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.validator.Const;
import com.youran.generate.constant.JoinType;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 关联DTO
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "关联DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinDTO {

    @ApiModelProperty(notes = "关联类型", example = "1", required = true, allowableValues = JoinType.VALUES_STR)
    @NotNull
    @Const(constClass = JoinType.class)
    private Integer joinType;

    @ApiModelProperty(notes = "左序号", example = "1")
    private Integer leftIndex;

    @ApiModelProperty(notes = "右序号", example = "1")
    private Integer rightIndex;

    @ApiModelProperty(notes = "左实体id", example = "1")
    private Integer leftEntityId;

    @ApiModelProperty(notes = "右实体id", example = "1")
    private Integer rightEntityId;

    @ApiModelProperty(notes = "左字段id", example = "1")
    private Integer leftFieldId;

    @ApiModelProperty(notes = "右字段id", example = "1")
    private Integer rightFieldId;

    @ApiModelProperty(notes = "左多对多id", example = "1")
    private Integer leftMtmId;

    @ApiModelProperty(notes = "右多对多id", example = "1")
    private Integer rightMtmId;

    @ApiModelProperty(notes = "左多对多字段", example = "1")
    private String leftMtmField;

    @ApiModelProperty(notes = "右多对多字段", example = "1")
    private String rightMtmField;


    /**
     * 左实体
     */
    @JsonIgnore
    private transient MetaEntityPO leftEntity;
    /**
     * 右实体
     */
    @JsonIgnore
    private transient MetaEntityPO rightEntity;
    /**
     * 左字段
     */
    @JsonIgnore
    private transient MetaFieldPO leftField;
    /**
     * 右字段
     */
    @JsonIgnore
    private transient MetaFieldPO rightField;
    /**
     * 左多对多
     */
    @JsonIgnore
    private transient MetaManyToManyPO leftMtm;
    /**
     * 右多对多
     */
    @JsonIgnore
    private transient MetaManyToManyPO rightMtm;


    public Integer getLeftIndex() {
        return leftIndex;
    }

    public void setLeftIndex(Integer leftIndex) {
        this.leftIndex = leftIndex;
    }

    public Integer getRightIndex() {
        return rightIndex;
    }

    public void setRightIndex(Integer rightIndex) {
        this.rightIndex = rightIndex;
    }

    public Integer getJoinType() {
        return joinType;
    }

    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
    }

    public Integer getLeftEntityId() {
        return leftEntityId;
    }

    public void setLeftEntityId(Integer leftEntityId) {
        this.leftEntityId = leftEntityId;
    }

    public Integer getRightEntityId() {
        return rightEntityId;
    }

    public void setRightEntityId(Integer rightEntityId) {
        this.rightEntityId = rightEntityId;
    }

    public Integer getLeftFieldId() {
        return leftFieldId;
    }

    public void setLeftFieldId(Integer leftFieldId) {
        this.leftFieldId = leftFieldId;
    }

    public Integer getRightFieldId() {
        return rightFieldId;
    }

    public void setRightFieldId(Integer rightFieldId) {
        this.rightFieldId = rightFieldId;
    }

    public Integer getLeftMtmId() {
        return leftMtmId;
    }

    public void setLeftMtmId(Integer leftMtmId) {
        this.leftMtmId = leftMtmId;
    }

    public Integer getRightMtmId() {
        return rightMtmId;
    }

    public void setRightMtmId(Integer rightMtmId) {
        this.rightMtmId = rightMtmId;
    }

    public String getLeftMtmField() {
        return leftMtmField;
    }

    public void setLeftMtmField(String leftMtmField) {
        this.leftMtmField = leftMtmField;
    }

    public String getRightMtmField() {
        return rightMtmField;
    }

    public void setRightMtmField(String rightMtmField) {
        this.rightMtmField = rightMtmField;
    }

    public MetaEntityPO getLeftEntity() {
        return leftEntity;
    }

    public void setLeftEntity(MetaEntityPO leftEntity) {
        this.leftEntity = leftEntity;
    }

    public MetaEntityPO getRightEntity() {
        return rightEntity;
    }

    public void setRightEntity(MetaEntityPO rightEntity) {
        this.rightEntity = rightEntity;
    }

    public MetaFieldPO getLeftField() {
        return leftField;
    }

    public void setLeftField(MetaFieldPO leftField) {
        this.leftField = leftField;
    }

    public MetaFieldPO getRightField() {
        return rightField;
    }

    public void setRightField(MetaFieldPO rightField) {
        this.rightField = rightField;
    }

    public MetaManyToManyPO getLeftMtm() {
        return leftMtm;
    }

    public void setLeftMtm(MetaManyToManyPO leftMtm) {
        this.leftMtm = leftMtm;
    }

    public MetaManyToManyPO getRightMtm() {
        return rightMtm;
    }

    public void setRightMtm(MetaManyToManyPO rightMtm) {
        this.rightMtm = rightMtm;
    }
}
