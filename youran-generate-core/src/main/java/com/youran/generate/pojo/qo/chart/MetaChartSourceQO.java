package com.youran.generate.pojo.qo.chart;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import static com.youran.generate.pojo.example.chart.MetaChartSourceExample.*;

/**
 * 查询【图表数据源】的参数
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartSourceQO extends AbstractQO {

    @ApiParam(value = N_PROJECT_ID,example = E_PROJECT_ID)
    private Integer projectId;

    @ApiParam(value = N_AGGREGATION,example = E_AGGREGATION)
    private Boolean aggregation;


    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Boolean getAggregation() {
        return this.aggregation;
    }

    public void setAggregation(Boolean aggregation) {
        this.aggregation = aggregation;
    }

}

