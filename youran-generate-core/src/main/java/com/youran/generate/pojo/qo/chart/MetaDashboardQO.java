package com.youran.generate.pojo.qo.chart;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import static com.youran.generate.pojo.example.chart.MetaDashboardExample.*;

/**
 * 查询【看板】的参数
 *
 * @author cbb
 * @date 2020/06/13
 */
public class MetaDashboardQO extends AbstractQO {

    @ApiParam(value = N_PROJECT_ID,example = E_PROJECT_ID)
    private Integer projectId;


    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}

