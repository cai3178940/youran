package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaManyToManyExample.E_MTMID;
import static com.youran.generate.pojo.example.MetaManyToManyExample.N_MTMID;

/**
 * 修改多对多关系参数
 *
 * @author: cbb
 * @date: 2017/7/4
 */
public class MetaManyToManyUpdateDTO extends MetaManyToManyAddDTO {

    @ApiModelProperty(notes = N_MTMID, example = E_MTMID)
    @NotNull
    private Integer mtmId;

    public Integer getMtmId() {
        return mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }
}
