package com.youran.generate.pojo.dto.chart.source;

import com.youran.common.validator.Const;
import com.youran.generate.constant.JoinType;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 关联DTO
 *
 * @author: cbb
 * @date: 2020-04-04
 */
public class JoinDTO {

    @ApiModelProperty(notes = "关联类型", example = "1", required = true, allowableValues = JoinType.VALUES_STR)
    @NotNull
    @Const(constClass = JoinType.class)
    private Integer joinType;

    @ApiModelProperty(notes = "左实体id", example = "1", required = true)
    @NotNull
    private Integer leftEntityId;


    @ApiModelProperty(notes = "右实体id", example = "1", required = true)
    @NotNull
    private Integer rightEntityId;

    @ApiModelProperty(notes = "左字段id", example = "1", required = true)
    @NotNull
    private Integer leftFieldId;

    @ApiModelProperty(notes = "右字段id", example = "1", required = true)
    @NotNull
    private Integer rightFieldId;

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
}
