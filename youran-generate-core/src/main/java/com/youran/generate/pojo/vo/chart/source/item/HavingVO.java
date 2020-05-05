package com.youran.generate.pojo.vo.chart.source.item;

/**
 * having条件
 *
 * @author: cbb
 * @date: 2020-05-05
 */
public class HavingVO extends MetaChartSourceItemVO {

    /**
     * 过滤运算符
     */
    private Integer filterOperator;

    /**
     * 过滤值
     */
    private String[] filterValue;

    public Integer getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(Integer filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String[] getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String[] filterValue) {
        this.filterValue = filterValue;
    }
}
