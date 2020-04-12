package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.BarLinePO;

import java.util.List;

/**
 * 柱线图上下文对象
 *
 * @author: cbb
 * @date: 2020-04-12
 */
public class BarLineContext extends AbstractChartContext<BarLinePO> {

    /**
     * 主X轴
     */
    private ChartItemDTO axisX;

    /**
     * 副X轴
     */
    private ChartItemDTO axisX2;

    /**
     * Y轴
     */
    private List<ChartItemDTO> axisYList;

    public BarLineContext(MetaProjectPO project, BarLinePO barLine) {
        super(project, barLine);
        this.axisX = barLine.getAxisX();
        this.axisX2 = barLine.getAxisX2();
        this.axisYList = barLine.getAxisYList();
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
