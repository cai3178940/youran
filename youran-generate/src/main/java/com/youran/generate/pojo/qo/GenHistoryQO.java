package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.PageQO;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.GenHistoryExample.E_PROJECTID;
import static com.youran.generate.pojo.example.GenHistoryExample.N_PROJECTID;

/**
 * <p>Title:分页查询参数</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
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
