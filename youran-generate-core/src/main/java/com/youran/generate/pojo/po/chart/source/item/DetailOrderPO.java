package com.youran.generate.pojo.po.chart.source.item;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;

import java.util.Map;

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
    public void assembleItem(MetaChartSourcePO chartSource) {
        super.assembleItem(chartSource);
        Map<Integer, DetailColumnPO> detailColumnMap = chartSource.getDetailColumnMap();
        detailColumnMap.entrySet().stream()
            .filter(entry -> entry.getKey().equals(this.getParentId()))
            .findFirst()
            .ifPresent(entry -> this.setParent(entry.getValue()));
    }

    /**
     * 将超类转成当前类型
     *
     * @param superPO
     * @param featureDeserialize
     * @return
     */
    public static DetailOrderPO fromSuperType(MetaChartSourceItemPO superPO,
                                              boolean featureDeserialize) {
        if (!SourceItemType.DETAIL_ORDER.getValue().equals(superPO.getType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        DetailOrderPO po = new DetailOrderPO();
        MetaChartSourceItemMapper.INSTANCE.copyProperties(po, superPO);
        if (featureDeserialize) {
            po.featureDeserialize();
        }
        return po;
    }

    @Override
    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.sortType = featureDTO.getSortType();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setSortType(this.sortType);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    static class FeatureDTO{
        private Integer sortType;

        public Integer getSortType() {
            return sortType;
        }

        public void setSortType(Integer sortType) {
            this.sortType = sortType;
        }
    }
}
