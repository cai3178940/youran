package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaIndexExample.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:12
 */
public class MetaIndexUpdateDTO extends MetaIndexAddDTO {

    @ApiModelProperty(notes = N_INDEXID, example = E_INDEXID)
    @NotNull
    private Integer indexId;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }
}
