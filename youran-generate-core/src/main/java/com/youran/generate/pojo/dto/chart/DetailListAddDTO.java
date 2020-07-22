package com.youran.generate.pojo.dto.chart;

import com.youran.generate.constant.ChartType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 新增【明细表】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "新增【明细表】入参")
public class DetailListAddDTO extends AbstractChartDTO {

    @ApiModelProperty(notes = "明细列")
    private List<ChartItemDTO> columnList;

    @ApiModelProperty(notes = "隐藏明细列")
    private List<ChartItemDTO> hiddenColumnList;

    @ApiModelProperty(notes = "默认每页记录数")
    private Integer defaultPageSize;

    @ApiModelProperty(notes = "是否支持excel导出")
    private Boolean excelExport;

    @Override
    public Integer getChartType() {
        return ChartType.DETAIL_LIST.getValue();
    }

    public List<ChartItemDTO> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ChartItemDTO> columnList) {
        this.columnList = columnList;
    }

    public List<ChartItemDTO> getHiddenColumnList() {
        return hiddenColumnList;
    }

    public void setHiddenColumnList(List<ChartItemDTO> hiddenColumnList) {
        this.hiddenColumnList = hiddenColumnList;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public Boolean getExcelExport() {
        return excelExport;
    }

    public void setExcelExport(Boolean excelExport) {
        this.excelExport = excelExport;
    }
}
