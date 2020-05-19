package com.youran.generate.pojo.vo.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;

import java.util.List;

/**
 * 明细表
 *
 * @author: cbb
 * @date: 2020-05-05
 */
public class DetailListVO extends MetaChartShowVO {

    /**
     * 明细列
     */
    private List<ChartItemDTO> columnList;
    /**
     * 默认每页记录数
     */
    private Integer defaultPageSize;

    public List<ChartItemDTO> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ChartItemDTO> columnList) {
        this.columnList = columnList;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}