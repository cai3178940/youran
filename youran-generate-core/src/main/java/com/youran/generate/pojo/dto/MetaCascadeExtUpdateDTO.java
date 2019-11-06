package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaCascadeExtExample.E_CASCADEEXTID;
import static com.youran.generate.pojo.example.MetaCascadeExtExample.N_CASCADEEXTID;

/**
 * 修改级联展示入参
 *
 * @author: cbb
 * @date: 2018/5/28
 */
@ApiModel(description = "修改级联展示入参")
public class MetaCascadeExtUpdateDTO extends MetaCascadeExtAddDTO {


    /**
     * 主键id
     */
    @ApiModelProperty(notes = N_CASCADEEXTID, example = E_CASCADEEXTID)
    @NotNull
    private Integer cascadeExtId;

    public Integer getCascadeExtId() {
        return cascadeExtId;
    }

    public void setCascadeExtId(Integer cascadeExtId) {
        this.cascadeExtId = cascadeExtId;
    }


}
