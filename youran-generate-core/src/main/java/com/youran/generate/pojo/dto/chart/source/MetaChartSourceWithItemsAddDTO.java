package com.youran.generate.pojo.dto.chart.source;

import com.youran.generate.pojo.dto.chart.source.item.*;

import java.util.List;

/**
 * 新增【图表数据源】带数据项的参数
 *
 * @author: cbb
 * @date: 2020-05-20
 */
public class MetaChartSourceWithItemsAddDTO extends MetaChartSourceAddDTO {

    private List<DetailColumnAddDTO> detailColumnList;
    private List<WhereAddDTO> whereList;
    private List<DetailOrderAddDTO> detailOrderList;
    private List<DimensionAddDTO> dimensionList;
    private List<MetricsAddDTO> metricsList;
    private List<HavingAddDTO> havingList;
    private List<AggOrderAddDTO> aggOrderList;

    public List<DetailColumnAddDTO> getDetailColumnList() {
        return detailColumnList;
    }

    public void setDetailColumnList(List<DetailColumnAddDTO> detailColumnList) {
        this.detailColumnList = detailColumnList;
    }

    public List<WhereAddDTO> getWhereList() {
        return whereList;
    }

    public void setWhereList(List<WhereAddDTO> whereList) {
        this.whereList = whereList;
    }

    public List<DetailOrderAddDTO> getDetailOrderList() {
        return detailOrderList;
    }

    public void setDetailOrderList(List<DetailOrderAddDTO> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    public List<DimensionAddDTO> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<DimensionAddDTO> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<MetricsAddDTO> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<MetricsAddDTO> metricsList) {
        this.metricsList = metricsList;
    }

    public List<HavingAddDTO> getHavingList() {
        return havingList;
    }

    public void setHavingList(List<HavingAddDTO> havingList) {
        this.havingList = havingList;
    }

    public List<AggOrderAddDTO> getAggOrderList() {
        return aggOrderList;
    }

    public void setAggOrderList(List<AggOrderAddDTO> aggOrderList) {
        this.aggOrderList = aggOrderList;
    }

}
