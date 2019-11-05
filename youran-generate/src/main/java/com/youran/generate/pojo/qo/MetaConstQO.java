package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.PageQO;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaConstExample.E_PROJECTID;
import static com.youran.generate.pojo.example.MetaConstExample.N_PROJECTID;

/**
 * 分页查询参数
 *
 * @author: cbb
 * @date: 2017/6/14
 */
public class MetaConstQO extends PageQO {

    @ApiParam(value = N_PROJECTID, example = E_PROJECTID)
    @NotNull
    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}
