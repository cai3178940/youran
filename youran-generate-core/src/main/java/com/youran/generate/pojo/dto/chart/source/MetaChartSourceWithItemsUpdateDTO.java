package com.youran.generate.pojo.dto.chart.source;

import com.youran.generate.pojo.dto.chart.source.item.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 修改【图表数据源】带数据项的参数
 * @author: cbb
 * @date: 2020-05-20
 */
public class MetaChartSourceWithItemsUpdateDTO extends MetaChartSourceUpdateDTO {

    private List<DetailColumnUpdateDTO> detailColumnList;
    private List<WhereUpdateDTO> whereList;
    private List<DetailOrderUpdateDTO> detailOrderList;
    private List<DimensionUpdateDTO> dimensionList;
    private List<MetricsUpdateDTO> metricsList;
    private List<HavingUpdateDTO> havingList;
    private List<AggOrderUpdateDTO> aggOrderList;


    public List<Integer> fetchSourceItemIds(){
        List<Integer> list = new ArrayList<>();
        list.addAll(this.convertIdList(detailColumnList,DetailColumnUpdateDTO::getSourceItemId));
        list.addAll(this.convertIdList(whereList,WhereUpdateDTO::getSourceItemId));
        list.addAll(this.convertIdList(detailOrderList,DetailOrderUpdateDTO::getSourceItemId));
        list.addAll(this.convertIdList(dimensionList,DimensionUpdateDTO::getSourceItemId));
        list.addAll(this.convertIdList(metricsList,MetricsUpdateDTO::getSourceItemId));
        list.addAll(this.convertIdList(havingList,HavingUpdateDTO::getSourceItemId));
        list.addAll(this.convertIdList(aggOrderList,AggOrderUpdateDTO::getSourceItemId));
        return list;
    }

    private <T> List<Integer> convertIdList(List<T> list, Function<T,Integer> mapper){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        return list.stream()
            .map(mapper)
            .filter(id -> id != null)
            .collect(Collectors.toList());
    }

    public List<DetailColumnUpdateDTO> getDetailColumnList() {
        return detailColumnList;
    }

    public void setDetailColumnList(List<DetailColumnUpdateDTO> detailColumnList) {
        this.detailColumnList = detailColumnList;
    }

    public List<WhereUpdateDTO> getWhereList() {
        return whereList;
    }

    public void setWhereList(List<WhereUpdateDTO> whereList) {
        this.whereList = whereList;
    }

    public List<DetailOrderUpdateDTO> getDetailOrderList() {
        return detailOrderList;
    }

    public void setDetailOrderList(List<DetailOrderUpdateDTO> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    public List<DimensionUpdateDTO> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<DimensionUpdateDTO> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<MetricsUpdateDTO> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<MetricsUpdateDTO> metricsList) {
        this.metricsList = metricsList;
    }

    public List<HavingUpdateDTO> getHavingList() {
        return havingList;
    }

    public void setHavingList(List<HavingUpdateDTO> havingList) {
        this.havingList = havingList;
    }

    public List<AggOrderUpdateDTO> getAggOrderList() {
        return aggOrderList;
    }

    public void setAggOrderList(List<AggOrderUpdateDTO> aggOrderList) {
        this.aggOrderList = aggOrderList;
    }
}
