package com.youran.generate.pojo.vo.chart;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.chart.MetaDashboardExample.*;

/**
 * 【看板】详情展示对象
 *
 * @author cbb
 * @date 2020/06/13
 */
@ApiModel(description = "【看板】详情展示对象")
public class MetaDashboardShowVO extends AbstractVO {

    @ApiModelProperty(notes = N_DASHBOARD_ID,example = E_DASHBOARD_ID)
    private Integer dashboardId;

    @ApiModelProperty(notes = N_NAME,example = E_NAME)
    private String name;

    @ApiModelProperty(notes = N_TITLE,example = E_TITLE)
    private String title;

    @ApiModelProperty(notes = N_MODULE,example = E_MODULE)
    private String module;

    @ApiModelProperty(notes = N_FEATURE,example = E_FEATURE)
    private String feature;

    @ApiModelProperty(notes = N_PROJECT_ID,example = E_PROJECT_ID)
    private Integer projectId;


    public Integer getDashboardId() {
        return this.dashboardId;
    }

    public void setDashboardId(Integer dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }



}

