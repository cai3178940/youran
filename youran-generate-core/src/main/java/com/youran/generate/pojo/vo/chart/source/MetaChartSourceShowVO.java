package com.youran.generate.pojo.vo.chart.source;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.chart.MetaChartSourceExample.*;

/**
 * 【图表数据源】详情展示对象
 *
 * @author cbb
 * @date 2020/04/04
 */
@ApiModel(description = "【图表数据源】详情展示对象")
public class MetaChartSourceShowVO extends AbstractVO {

    @ApiModelProperty(notes = N_SOURCE_ID,example = E_SOURCE_ID)
    private Integer sourceId;

    @ApiModelProperty(notes = N_PROJECT_ID,example = E_PROJECT_ID)
    private Integer projectId;

    @ApiModelProperty(notes = N_FEATURE,example = E_FEATURE)
    private String feature;

    @ApiModelProperty(notes = N_AGGREGATION,example = E_AGGREGATION)
    private Boolean aggregation;


    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Boolean getAggregation() {
        return this.aggregation;
    }

    public void setAggregation(Boolean aggregation) {
        this.aggregation = aggregation;
    }



}

