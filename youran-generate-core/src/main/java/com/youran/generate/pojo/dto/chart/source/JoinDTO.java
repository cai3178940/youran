package com.youran.generate.pojo.dto.chart.source;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.validator.Const;
import com.youran.generate.constant.JoinType;
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

    @ApiModelProperty(notes = "左侧关联", required = true)
    private JoinPartDTO left;

    @ApiModelProperty(notes = "右侧关联", required = true)
    private JoinPartDTO right;


    public Integer getJoinType() {
        return joinType;
    }

    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
    }

    public JoinPartDTO getLeft() {
        return left;
    }

    public void setLeft(JoinPartDTO left) {
        this.left = left;
    }

    public JoinPartDTO getRight() {
        return right;
    }

    public void setRight(JoinPartDTO right) {
        this.right = right;
    }
}
