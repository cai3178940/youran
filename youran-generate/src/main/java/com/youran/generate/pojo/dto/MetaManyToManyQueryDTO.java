package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaManyToManyExample.*;


/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/7/4 16:42
 */
public class MetaManyToManyQueryDTO {

    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    @NotNull
    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
