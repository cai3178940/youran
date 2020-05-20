package com.youran.generate.pojo.vo.chart.source;

import com.youran.generate.pojo.vo.chart.source.item.*;

import java.util.List;

/**
 * 【图表数据源】带数据项的详情展示对象
 *
 * @author: cbb
 * @date: 2020-05-20
 */
public class MetaChartSourceWithItemsShowVO extends MetaChartSourceShowVO {

    private List<DetailColumnVO> detailColumnList;
    private List<WhereVO> whereList;
    private List<DetailOrderVO> detailOrderList;
    private List<DimensionVO> dimensionList;
    private List<MetricsVO> metricsList;
    private List<HavingVO> havingList;
    private List<AggOrderVO> aggOrderList;

    public List<DetailColumnVO> getDetailColumnList() {
        return detailColumnList;
    }

    public void setDetailColumnList(List<DetailColumnVO> detailColumnList) {
        this.detailColumnList = detailColumnList;
    }

    public List<WhereVO> getWhereList() {
        return whereList;
    }

    public void setWhereList(List<WhereVO> whereList) {
        this.whereList = whereList;
    }

    public List<DetailOrderVO> getDetailOrderList() {
        return detailOrderList;
    }

    public void setDetailOrderList(List<DetailOrderVO> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    public List<DimensionVO> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<DimensionVO> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<MetricsVO> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<MetricsVO> metricsList) {
        this.metricsList = metricsList;
    }

    public List<HavingVO> getHavingList() {
        return havingList;
    }

    public void setHavingList(List<HavingVO> havingList) {
        this.havingList = havingList;
    }

    public List<AggOrderVO> getAggOrderList() {
        return aggOrderList;
    }

    public void setAggOrderList(List<AggOrderVO> aggOrderList) {
        this.aggOrderList = aggOrderList;
    }
}
