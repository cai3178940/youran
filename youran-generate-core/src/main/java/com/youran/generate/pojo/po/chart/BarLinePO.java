package com.youran.generate.pojo.po.chart;

import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

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
    private ChartItemDTO axisX;

    /**
     * 附加X轴
     */
    private ChartItemDTO axisX2;

    /**
     * Y轴
     */
    private List<ChartItemDTO> axisYList;

    public BarLinePO() {
        this.setChartType(ChartType.BAR_LINE.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartFeatureDTO featureDTO = FeatureMapper.asChartFeatureDTO(this.getFeature());
        this.axisX = featureDTO.getAxisX();
        this.axisX2 = featureDTO.getAxisX2();
        this.axisYList = featureDTO.getAxisYList();
    }

    @Override
    public void featureSerialize() {
        ChartFeatureDTO featureDTO = new ChartFeatureDTO();
        featureDTO.setAxisX(this.axisX);
        featureDTO.setAxisX2(this.axisX2);
        featureDTO.setAxisYList(this.axisYList);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public ChartItemDTO getAxisX() {
        return axisX;
    }

    public void setAxisX(ChartItemDTO axisX) {
        this.axisX = axisX;
    }

    public ChartItemDTO getAxisX2() {
        return axisX2;
    }

    public void setAxisX2(ChartItemDTO axisX2) {
        this.axisX2 = axisX2;
    }

    public List<ChartItemDTO> getAxisYList() {
        return axisYList;
    }

    public void setAxisYList(List<ChartItemDTO> axisYList) {
        this.axisYList = axisYList;
    }
}
