package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaProjectExample.*;

/**
 * <p>Title:修改项目DTO</p>
 * <p>Description:</p>
 * @author: cbb
 * Create Time:2017/5/24
 */
@ApiModel(description = "修改项目参数")
public class MetaProjectUpdateDTO extends MetaProjectAddDTO {

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
