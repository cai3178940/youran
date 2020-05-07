package com.youran.generate.pojo.qo.chart;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import static com.youran.generate.pojo.example.chart.MetaChartSourceItemExample.*;

/**
 * 查询【图表数据源项】的参数
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartSourceItemQO extends AbstractQO {

    @ApiParam(value = N_PROJECT_ID,example = E_PROJECT_ID)
    private Integer projectId;

    @ApiParam(value = N_SOURCE_ID,example = E_SOURCE_ID)
    private Integer sourceId;

    @ApiParam(value = N_JOIN_INDEX,example = E_JOIN_INDEX)
    private Integer joinIndex;

    @ApiParam(hidden = true)
    private Integer type;


    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getJoinIndex() {
        return this.joinIndex;
    }

    public void setJoinIndex(Integer joinIndex) {
        this.joinIndex = joinIndex;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

