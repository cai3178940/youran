package com.youran.generate.pojo.po.chart.source.item;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;

import java.util.Map;

/**
 * having条件
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class HavingPO extends MetaChartSourceItemPO {

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
    public void assembleItem(MetaChartSourcePO chartSource) {
        super.assembleItem(chartSource);
        Map<Integer, MetricsPO> metricsMap = chartSource.getMetricsMap();
        metricsMap.entrySet().stream()
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
    public static HavingPO fromSuperType(MetaChartSourceItemPO superPO,
                                         boolean featureDeserialize) {
        if (!SourceItemType.HAVING.getValue().equals(superPO.getType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        HavingPO po = new HavingPO();
        MetaChartSourceItemMapper.INSTANCE.copyProperties(po, superPO);
        if (featureDeserialize) {
            po.featureDeserialize();
        }
        return po;
    }

    @Override
    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.filterOperator = featureDTO.getFilterOperator();
        this.filterValue = featureDTO.getFilterValue();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setFilterOperator(this.filterOperator);
        featureDTO.setFilterValue(this.filterValue);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
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

    static class FeatureDTO{
        private Integer filterOperator;
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
}
