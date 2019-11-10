package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaMtmCascadeExtExample.E_MTM_CASCADE_EXT_ID;
import static com.youran.generate.pojo.example.MetaMtmCascadeExtExample.N_MTM_CASCADE_EXT_ID;

/**
 * 修改【多对多级联扩展】的参数
 *
 * @author cbb
 * @date 2019/09/21
 */
@ApiModel(description = "修改【多对多级联扩展】的参数")
public class MetaMtmCascadeExtUpdateDTO extends MetaMtmCascadeExtAddDTO {

    @ApiModelProperty(notes = N_MTM_CASCADE_EXT_ID, example = E_MTM_CASCADE_EXT_ID, required = true)
    @NotNull
    private Integer mtmCascadeExtId;

    public Integer getMtmCascadeExtId() {
        return this.mtmCascadeExtId;
    }

    public void setMtmCascadeExtId(Integer mtmCascadeExtId) {
        this.mtmCascadeExtId = mtmCascadeExtId;
    }

}

