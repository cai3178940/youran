package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaEntityExample.*;

/**
 * Title:修改实体DTO
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:05
 */
@ApiModel(description = "修改实体参数")
public class MetaEntityUpdateDTO extends MetaEntityAddDTO {

    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    @NotNull
    private Integer entityId;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

}
