package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import com.youran.generate.constant.MetaConstType;
import com.youran.generate.constant.PatternConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.youran.generate.pojo.example.MetaConstExample.*;

/**
 * 新增常量类DTO
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@ApiModel(description = "新增常量类参数")
public class MetaConstAddDTO extends AbstractDTO {


    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    @NotNull
    private Integer projectId;

    @ApiModelProperty(notes = N_CONSTNAME, example = E_CONSTNAME)
    @Length(max = 20, message = "constName最大长度不能超过{max}")
    @Pattern(regexp = PatternConst.CLASS_NAME, message = PatternConst.CLASS_NAME_MSG)
    private String constName;

    @ApiModelProperty(notes = N_CONSTREMARK, example = E_CONSTREMARK)
    @NotNull
    @Length(max = 50, message = "constRemark最大长度不能超过{max}")
    private String constRemark;

    @ApiModelProperty(notes = N_CONSTTYPE, example = E_CONSTTYPE)
    @NotNull
    @Const(constClass = MetaConstType.class)
    private Integer constType;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

    public String getConstRemark() {
        return constRemark;
    }

    public void setConstRemark(String constRemark) {
        this.constRemark = constRemark;
    }

    public Integer getConstType() {
        return constType;
    }

    public void setConstType(Integer constType) {
        this.constType = constType;
    }
}
