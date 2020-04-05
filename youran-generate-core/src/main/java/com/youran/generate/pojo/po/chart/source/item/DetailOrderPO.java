package com.youran.generate.pojo.po.chart.source.item;

import com.youran.generate.constant.SourceItemSubType;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

/**
 * 明细排序
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class DetailOrderPO extends MetaChartSourceItemPO {

    /**
     * 父明细列id
     */
    private Integer parentId;

    /**
     * 排序方式
     */
    private Integer sortType;

    public DetailOrderPO() {
        this.setType(SourceItemType.DETAIL_ORDER.getValue());
        this.setSubType(SourceItemSubType.NONE.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartSourceItemFeatureDTO featureDTO = FeatureMapper.asChartSourceItemFeatureDTO(this.getFeature());
        this.parentId = featureDTO.getParentId();
        this.sortType = featureDTO.getSortType();
    }

    @Override
    public void featureSerialize() {
        ChartSourceItemFeatureDTO featureDTO = new ChartSourceItemFeatureDTO();
        featureDTO.setParentId(this.parentId);
        featureDTO.setSortType(this.sortType);
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

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
