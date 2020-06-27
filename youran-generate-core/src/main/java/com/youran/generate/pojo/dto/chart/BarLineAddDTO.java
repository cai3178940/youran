package com.youran.generate.pojo.dto.chart;

import com.youran.generate.constant.ChartType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 新增【柱线图】入参
 *
 * @author: cbb
 * @date: 2020-04-05
 */
@ApiModel(description = "新增【柱线图】入参")
public class BarLineAddDTO extends AbstractChartDTO {

    @ApiModelProperty(notes = "主X轴(维度)")
    private ChartItemDTO axisX;

    @ApiModelProperty(notes = "附加维度")
    private ChartItemDTO axisX2;

    @ApiModelProperty(notes = "Y轴(指标)")
    private List<ChartItemDTO> axisYList;

    @ApiModelProperty(notes = "图表配置项模板")
    private String optionTemplate;

    @Override
    public Integer getChartType() {
        return ChartType.BAR_LINE.getValue();
    }

    public ChartItemDTO getAxisX() {
        return axisX;
    }

    public void setAxisX(ChartItemDTO axisX) {
        this.axisX = axisX;
    }

    public ChartItemDTO getAxisX2() {
        return axisX2;
    }

    public void setAxisX2(ChartItemDTO axisX2) {
        this.axisX2 = axisX2;
    }

    public List<ChartItemDTO> getAxisYList() {
        return axisYList;
    }

    public void setAxisYList(List<ChartItemDTO> axisYList) {
        this.axisYList = axisYList;
    }

    public String getOptionTemplate() {
        return optionTemplate;
    }

    public void setOptionTemplate(String optionTemplate) {
        this.optionTemplate = optionTemplate;
    }
}
