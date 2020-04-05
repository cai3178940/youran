package com.youran.generate.pojo.po.chart.source.item;

import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

/**
 * having条件
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class HavingPO extends MetaChartSourceItemPO {

    /**
     * 父指标id
     */
    private Integer parentId;

    /**
     * 过滤运算符
     */
    private Integer filterOperator;

    /**
     * 过滤值
     */
    private String[] filterValue;

    public HavingPO() {
        this.setType(SourceItemType.HAVING.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartSourceItemFeatureDTO featureDTO = FeatureMapper.asChartSourceItemFeatureDTO(this.getFeature());
        this.parentId = featureDTO.getParentId();
        this.filterOperator = featureDTO.getFilterOperator();
        this.filterValue = featureDTO.getFilterValue();
    }

    @Override
    public void featureSerialize() {
        ChartSourceItemFeatureDTO featureDTO = new ChartSourceItemFeatureDTO();
        featureDTO.setParentId(this.parentId);
        featureDTO.setFilterOperator(this.filterOperator);
        featureDTO.setFilterValue(this.filterValue);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    @Override
    public Integer getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

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
