package com.youran.generate.pojo.vo.chart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youran.common.constant.JsonFieldConst;
import com.youran.common.pojo.vo.AbstractVO;
import com.youran.generate.constant.ChartType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import static com.youran.generate.pojo.example.chart.MetaChartExample.*;

/**
 * 【图表】列表展示对象
 *
 * @author cbb
 * @date 2020/04/04
 */
@ApiModel(description = "【图表】列表展示对象")
public class MetaChartListVO extends AbstractVO {

    @ApiModelProperty(notes = N_CHART_ID, example = E_CHART_ID)
    private Integer chartId;

    @ApiModelProperty(notes = N_PROJECT_ID, example = E_PROJECT_ID)
    private Integer projectId;

    @ApiModelProperty(notes = N_CHART_TYPE, example = E_CHART_TYPE, allowableValues = ChartType.VALUES_STR)
    private Integer chartType;

    @ApiModelProperty(notes = N_CHART_NAME, example = E_CHART_NAME)
    private String chartName;

    @ApiModelProperty(notes = N_MODULE, example = E_MODULE)
    private String module;

    @ApiModelProperty(notes = N_TITLE, example = E_TITLE)
    private String title;

    @ApiModelProperty(notes = N_CREATED_TIME, example = E_CREATED_TIME)
    @JsonFormat(pattern = JsonFieldConst.DEFAULT_DATETIME_FORMAT, timezone = "GMT+8")
    private Date createdTime;

    @ApiModelProperty(notes = N_OPERATED_TIME, example = E_OPERATED_TIME)
    @JsonFormat(pattern = JsonFieldConst.DEFAULT_DATETIME_FORMAT, timezone = "GMT+8")
    private Date operatedTime;


    public Integer getChartId() {
        return this.chartId;
    }

    public void setChartId(Integer chartId) {
        this.chartId = chartId;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getChartType() {
        return this.chartType;
    }

    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    public String getChartName() {
        return this.chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getOperatedTime() {
        return this.operatedTime;
    }

    public void setOperatedTime(Date operatedTime) {
        this.operatedTime = operatedTime;
    }



}

