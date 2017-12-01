package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaConstExample.*;
/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/6/14 9:32
 */
@ApiModel(description = "分页查询参数")
public class MetaConstQueryDTO extends PageQueryDTO {

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
