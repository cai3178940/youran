package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.constant.PatternConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.youran.generate.pojo.example.MetaConstDetailExample.*;


/**
 * <p>Title:新增常量值DTO</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@ApiModel(description = "新增常量值参数")
public class MetaConstDetailAddDTO extends AbstractDTO {


    @ApiModelProperty(notes = N_CONSTID, example = E_CONSTID)
    @NotNull
    private Integer constId;

    @ApiModelProperty(notes = N_DETAILNAME, example = E_DETAILNAME)
    @Length(max = 50, message = "detailName最大长度不能超过{max}")
    @Pattern(regexp = PatternConst.CONST_NAME, message = PatternConst.CONST_NAME_MSG)
    private String detailName;

    @ApiModelProperty(notes = N_DETAILVALUE, example = E_DETAILVALUE)
    @NotNull
    @Length(max = 50, message = "detailValue最大长度不能超过{max}")
    private String detailValue;

    @ApiModelProperty(notes = N_DETAILREMARK, example = E_DETAILREMARK)
    @NotNull
    @Length(max = 50, message = "detailRemark最大长度不能超过{max}")
    private String detailRemark;

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }
}
