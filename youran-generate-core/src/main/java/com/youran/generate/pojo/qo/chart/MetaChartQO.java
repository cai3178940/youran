package com.youran.generate.pojo.qo.chart;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;

import static com.youran.generate.pojo.example.chart.MetaChartExample.*;

/**
 * 查询【图表】的参数
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartQO extends AbstractQO {

    @ApiParam(value = N_PROJECT_ID,example = E_PROJECT_ID)
    private Integer projectId;

    @ApiParam(value = N_CHART_TYPE,example = E_CHART_TYPE)
    private Integer chartType;

    @ApiParam(value = N_CHART_NAME,example = E_CHART_NAME)
    @Length(max = 64,message = "chartName最大长度不能超过{max}")
    private String chartName;

    @ApiParam(value = "模块排序标识【1升序,-1降序,0不排序】", example = "1")
    private Integer moduleSortSign;

    public Integer getModuleSortSign() {
        return moduleSortSign;
    }

    public void setModuleSortSign(Integer moduleSortSign) {
        this.moduleSortSign = moduleSortSign;
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

}

