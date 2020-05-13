package com.youran.generate.pojo.vo.chart.source;

import com.youran.common.pojo.vo.AbstractVO;
import com.youran.generate.pojo.dto.chart.source.JoinDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    @ApiModelProperty(notes = N_AGGREGATION,example = E_AGGREGATION)
    private Boolean aggregation;

    @ApiModelProperty(notes = "主实体id", example = "1", required = true)
    @NotNull
    private Integer entityId;

    @ApiModelProperty(notes = "关联")
    private List<JoinDTO> joins;

    @ApiModelProperty(notes = "数量限制", example = "1000")
    private Integer limit;


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

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public List<JoinDTO> getJoins() {
        return joins;
    }

    public void setJoins(List<JoinDTO> joins) {
        this.joins = joins;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getAggregation() {
        return this.aggregation;
    }

    public void setAggregation(Boolean aggregation) {
        this.aggregation = aggregation;
    }



}

