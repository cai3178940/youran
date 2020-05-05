package com.youran.generate.pojo.vo.chart;

import com.youran.common.pojo.vo.AbstractVO;
import com.youran.generate.constant.ChartType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.chart.MetaChartExample.*;

/**
 * 【图表】详情展示对象
 *
 * @author cbb
 * @date 2020/04/04
 */
@ApiModel(description = "【图表】详情展示对象")
public class MetaChartShowVO extends AbstractVO {

    @ApiModelProperty(notes = N_CHART_ID,example = E_CHART_ID)
    private Integer chartId;

    @ApiModelProperty(notes = N_PROJECT_ID,example = E_PROJECT_ID)
    private Integer projectId;

    @ApiModelProperty(notes = N_SOURCE_ID,example = E_SOURCE_ID)
    private Integer sourceId;

    @ApiModelProperty(notes = N_CHART_TYPE,example = E_CHART_TYPE, allowableValues = ChartType.VALUES_STR)
    private Integer chartType;

    @ApiModelProperty(notes = N_CHART_NAME,example = E_CHART_NAME)
    private String chartName;

    @ApiModelProperty(notes = N_MODULE,example = E_MODULE)
    private String module;

    @ApiModelProperty(notes = N_TITLE,example = E_TITLE)
    private String title;

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

    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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

}

