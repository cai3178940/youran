package com.youran.generate.pojo.vo.chart.source.item;

/**
 * 明细排序
 *
 * @author: cbb
 * @date: 2020-05-05
 */
public class DetailOrderVO extends MetaChartSourceItemVO {

    /**
     * 排序方式
     */
    private Integer sortType;

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
