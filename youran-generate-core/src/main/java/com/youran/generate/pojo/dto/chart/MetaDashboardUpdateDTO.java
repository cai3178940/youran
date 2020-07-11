package com.youran.generate.pojo.dto.chart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.chart.MetaDashboardExample.E_DASHBOARD_ID;
import static com.youran.generate.pojo.example.chart.MetaDashboardExample.N_DASHBOARD_ID;

/**
 * 修改【看板】的参数
 *
 * @author cbb
 * @date 2020/06/13
 */
@ApiModel(description = "修改【看板】的参数")
public class MetaDashboardUpdateDTO extends MetaDashboardAddDTO {

    @ApiModelProperty(notes = N_DASHBOARD_ID,example = E_DASHBOARD_ID,required = true)
    @NotNull
    private Integer dashboardId;

    public Integer getDashboardId() {
        return this.dashboardId;
    }

    public void setDashboardId(Integer dashboardId) {
        this.dashboardId = dashboardId;
    }

}

