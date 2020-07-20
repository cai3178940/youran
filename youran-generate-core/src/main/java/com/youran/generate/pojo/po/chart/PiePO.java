package com.youran.generate.pojo.po.chart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;

import java.util.Map;

/**
 * 饼图
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class PiePO extends MetaChartPO {

    /**
     * 维度项
     */
    @JsonIgnore
    private ChartItemDTO<DimensionPO> dimension;

    /**
     * 指标项
     */
    @JsonIgnore
    private ChartItemDTO<MetricsPO> metrics;

    /**
     * 图表配置项模板
     */
    @JsonIgnore
    private String optionTemplate;

    public PiePO() {
        this.setChartType(ChartType.PIE.getValue());
    }


    /**
     * 将超类转成当前类型
     *
     * @param superPO
     * @param featureDeserialize
     * @return
     */
    public static PiePO fromSuperType(MetaChartPO superPO,
                                      boolean featureDeserialize) {
        if (!ChartType.PIE.getValue().equals(superPO.getChartType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        PiePO po = new PiePO();
        MetaChartMapper.INSTANCE.copyProperties(po, superPO);
        if (featureDeserialize) {
            po.featureDeserialize();
        }
        return po;
    }

    @Override
    public void assemble(MetaChartSourcePO chartSource) {
        super.assemble(chartSource);
        Integer dimensionSourceItemId = dimension.getSourceItemId();
        DimensionPO dimension = chartSource.getDimensionMap().get(dimensionSourceItemId);
        if (dimension != null) {
            this.dimension.setSourceItem(dimension);
        } else {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                "图表【" + this.getTitle() + "】维度项不存在，sourceItemId=" + dimensionSourceItemId);
        }
        Integer metricsSourceItemId = metrics.getSourceItemId();
        MetricsPO metrics = chartSource.getMetricsMap().get(metricsSourceItemId);
        if (metrics != null) {
            this.metrics.setSourceItem(metrics);
        } else {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                "图表【" + this.getTitle() + "】指标项不存在，sourceItemId=" + metricsSourceItemId);
        }
    }

    @Override
    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.dimension = featureDTO.getDimension();
        this.metrics = featureDTO.getMetrics();
        this.optionTemplate = featureDTO.getOptionTemplate();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setDimension(this.dimension);
        featureDTO.setMetrics(this.metrics);
        featureDTO.setOptionTemplate(this.optionTemplate);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }

    @Override
    public void convertItemId(Map<Integer, Integer> metaChartSourceItemIdMap) {
        if (dimension != null) {
            dimension.setSourceItemId(metaChartSourceItemIdMap.get(dimension.getSourceItemId()));
        }
        if (metrics != null) {
            metrics.setSourceItemId(metaChartSourceItemIdMap.get(metrics.getSourceItemId()));
        }
    }

    public ChartItemDTO<DimensionPO> getDimension() {
        return dimension;
    }

    public void setDimension(ChartItemDTO<DimensionPO> dimension) {
        this.dimension = dimension;
    }

    public ChartItemDTO<MetricsPO> getMetrics() {
        return metrics;
    }

    public void setMetrics(ChartItemDTO<MetricsPO> metrics) {
        this.metrics = metrics;
    }

    public String getOptionTemplate() {
        return optionTemplate;
    }

    public void setOptionTemplate(String optionTemplate) {
        this.optionTemplate = optionTemplate;
    }

    static class FeatureDTO {
        private ChartItemDTO<DimensionPO> dimension;
        private ChartItemDTO<MetricsPO> metrics;
        private String optionTemplate;

        public ChartItemDTO<DimensionPO> getDimension() {
            return dimension;
        }

        public void setDimension(ChartItemDTO<DimensionPO> dimension) {
            this.dimension = dimension;
        }

        public ChartItemDTO<MetricsPO> getMetrics() {
            return metrics;
        }

        public void setMetrics(ChartItemDTO<MetricsPO> metrics) {
            this.metrics = metrics;
        }

        public String getOptionTemplate() {
            return optionTemplate;
        }

        public void setOptionTemplate(String optionTemplate) {
            this.optionTemplate = optionTemplate;
        }
    }
}
