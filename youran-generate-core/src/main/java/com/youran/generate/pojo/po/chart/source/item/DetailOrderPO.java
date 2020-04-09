package com.youran.generate.pojo.po.chart.source.item;

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
     * 排序方式
     */
    private Integer sortType;

    public DetailOrderPO() {
        this.setType(SourceItemType.DETAIL_ORDER.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartSourceItemFeatureDTO featureDTO = FeatureMapper.asChartSourceItemFeatureDTO(this.getFeature());
        this.sortType = featureDTO.getSortType();
    }

    @Override
    public void featureSerialize() {
        ChartSourceItemFeatureDTO featureDTO = new ChartSourceItemFeatureDTO();
        featureDTO.setSortType(this.sortType);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
