package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.PageQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaEntityExample.*;
/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 18:32
 */
@ApiModel(description = "分页查询参数")
public class MetaEntityQO extends PageQO {

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
