package com.youran.generate.pojo.po.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
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
    private ChartItemDTO<DimensionPO> axisX;

    /**
     * 副X轴
     */
    private ChartItemDTO<DimensionPO> axisX2;

    /**
     * Y轴
     */
    private List<ChartItemDTO<MetricsPO>> axisYList;

    public BarLinePO() {
        this.setChartType(ChartType.BAR_LINE.getValue());
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
        ChartFeatureDTO featureDTO = FeatureMapper.asChartFeatureDTO(this.getFeature());
        this.axisX = featureDTO.getAxisX();
        this.axisX2 = featureDTO.getAxisX2();
        this.axisYList = (List<ChartItemDTO<MetricsPO>>) featureDTO.getAxisYList();
    }

    @Override
    public void featureSerialize() {
        ChartFeatureDTO featureDTO = new ChartFeatureDTO();
        featureDTO.setAxisX(this.axisX);
        featureDTO.setAxisX2(this.axisX2);
        featureDTO.setAxisYList(this.axisYList);
        this.setFeature(FeatureMapper.asString(featureDTO));
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
}
