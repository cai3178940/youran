package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaConstDetailExample.*;


/**
 * Title:修改常量值DTO
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:05
 */
@ApiModel(description = "修改常量值参数")
public class MetaConstDetailUpdateDTO extends MetaConstDetailAddDTO {


    @ApiModelProperty(notes = N_CONSTDETAILID, example = E_CONSTDETAILID)
    @NotNull
    private Integer constDetailId;

    public Integer getConstDetailId() {
        return constDetailId;
    }

    public void setConstDetailId(Integer constDetailId) {
        this.constDetailId = constDetailId;
    }
}
