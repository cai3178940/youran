package com.youran.generate.pojo.vo.chart.source;

import com.youran.generate.pojo.vo.chart.source.item.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 【图表数据源】带数据项的详情展示对象
 *
 * @author: cbb
 * @date: 2020-05-20
 */
public class MetaChartSourceWithItemsShowVO extends MetaChartSourceShowVO {

    private List<DetailColumnVO> detailColumnList = new ArrayList<>();
    private List<WhereVO> whereList = new ArrayList<>();
    private List<DetailOrderVO> detailOrderList = new ArrayList<>();
    private List<DimensionVO> dimensionList = new ArrayList<>();
    private List<MetricsVO> metricsList = new ArrayList<>();
    private List<HavingVO> havingList = new ArrayList<>();
    private List<AggOrderVO> aggOrderList = new ArrayList<>();

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
