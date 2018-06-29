package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.PageQO;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.GenHistoryExample.E_PROJECTID;
import static com.youran.generate.pojo.example.GenHistoryExample.N_PROJECTID;

/**
 * Title: 分页查询参数
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 18:32
 */
public class GenHistoryQO extends PageQO {

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
