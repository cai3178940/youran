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

import java.util.List;

/**
 * 柱线图
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class BarLinePO extends MetaChartPO {

    /**
     * 主X轴
     */
    @JsonIgnore
    private ChartItemDTO<DimensionPO> axisX;

    /**
     * 副X轴
     */
    @JsonIgnore
    private ChartItemDTO<DimensionPO> axisX2;

    /**
     * Y轴
     */
    @JsonIgnore
    private List<ChartItemDTO<MetricsPO>> axisYList;

    /**
     * 图表配置项模板
     */
    @JsonIgnore
    private String optionTemplate;

    public BarLinePO() {
        this.setChartType(ChartType.BAR_LINE.getValue());
    }

    /**
     * 将超类转成当前类型
     *
     * @param superPO
     * @param featureDeserialize
     * @return
     */
    public static BarLinePO fromSuperType(MetaChartPO superPO,
                                             boolean featureDeserialize) {
        if (!ChartType.BAR_LINE.getValue().equals(superPO.getChartType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        BarLinePO po = new BarLinePO();
        MetaChartMapper.INSTANCE.copyProperties(po, superPO);
        if (featureDeserialize) {
            po.featureDeserialize();
        }
        return po;
    }

    @Override
    public void assemble(MetaChartSourcePO chartSource) {
        super.assemble(chartSource);

        Integer axisXSourceItemId = axisX.getSourceItemId();
        DimensionPO axisXDimension = chartSource.getDimensionMap().get(axisXSourceItemId);
        if (axisXDimension != null) {
            axisX.setSourceItem(axisXDimension);
        } else {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                "图表【" + this.getTitle() + "】主X轴不存在，sourceItemId=" + axisXSourceItemId);
        }

        if(axisX2!=null) {
            Integer axisX2SourceItemId = axisX2.getSourceItemId();
            DimensionPO axisX2Dimension = chartSource.getDimensionMap().get(axisX2SourceItemId);
            if (axisX2Dimension != null) {
                axisX2.setSourceItem(axisX2Dimension);
            } else {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "图表【" + this.getTitle() + "】副X轴不存在，sourceItemId=" + axisX2SourceItemId);
            }
        }

        for (ChartItemDTO chartItem : axisYList) {
            Integer sourceItemId = chartItem.getSourceItemId();
            MetricsPO metrics = chartSource.getMetricsMap().get(sourceItemId);
            if (metrics != null) {
                chartItem.setSourceItem(metrics);
            } else {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "图表【" + this.getTitle() + "】Y轴不存在，sourceItemId=" + sourceItemId);
            }
        }
    }

    @Override
    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.axisX = featureDTO.getAxisX();
        this.axisX2 = featureDTO.getAxisX2();
        this.axisYList = featureDTO.getAxisYList();
        this.optionTemplate = featureDTO.getOptionTemplate();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setAxisX(this.axisX);
        featureDTO.setAxisX2(this.axisX2);
        featureDTO.setAxisYList(this.axisYList);
        featureDTO.setOptionTemplate(this.optionTemplate);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }

    public ChartItemDTO<DimensionPO> getAxisX() {
        return axisX;
    }

    public void setAxisX(ChartItemDTO<DimensionPO> axisX) {
        this.axisX = axisX;
    }

    public ChartItemDTO<DimensionPO> getAxisX2() {
        return axisX2;
    }

    public void setAxisX2(ChartItemDTO<DimensionPO> axisX2) {
        this.axisX2 = axisX2;
    }

    public List<ChartItemDTO<MetricsPO>> getAxisYList() {
        return axisYList;
    }

    public void setAxisYList(List<ChartItemDTO<MetricsPO>> axisYList) {
        this.axisYList = axisYList;
    }

    public String getOptionTemplate() {
        return optionTemplate;
    }

    public void setOptionTemplate(String optionTemplate) {
        this.optionTemplate = optionTemplate;
    }

    static class FeatureDTO{
        private ChartItemDTO<DimensionPO> axisX;
        private ChartItemDTO<DimensionPO> axisX2;
        private List<ChartItemDTO<MetricsPO>> axisYList;
        private String optionTemplate;

        public ChartItemDTO<DimensionPO> getAxisX() {
            return axisX;
        }

        public void setAxisX(ChartItemDTO<DimensionPO> axisX) {
            this.axisX = axisX;
        }

        public ChartItemDTO<DimensionPO> getAxisX2() {
            return axisX2;
        }

        public void setAxisX2(ChartItemDTO<DimensionPO> axisX2) {
            this.axisX2 = axisX2;
        }

        public List<ChartItemDTO<MetricsPO>> getAxisYList() {
            return axisYList;
        }

        public void setAxisYList(List<ChartItemDTO<MetricsPO>> axisYList) {
            this.axisYList = axisYList;
        }

        public String getOptionTemplate() {
            return optionTemplate;
        }

        public void setOptionTemplate(String optionTemplate) {
            this.optionTemplate = optionTemplate;
        }
    }

}
