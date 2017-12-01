package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaManyToManyExample.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/7/4 16:39
 */
public class MetaManyToManyUpdateDTO extends MetaManyToManyAddDTO{

    @ApiModelProperty(notes = N_MTMID, example = E_MTMID)
    @NotNull
    private Integer mtmId;

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getMtmId() {
        return mtmId;
    }
}
