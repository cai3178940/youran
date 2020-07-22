package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.BarLinePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;

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
    private final ChartItemDTO<DimensionPO> axisX;

    /**
     * 副X轴
     */
    private final ChartItemDTO<DimensionPO> axisX2;

    /**
     * Y轴
     */
    private final List<ChartItemDTO<MetricsPO>> axisYList;

    /**
     * 图表配置项模板
     */
    private final String optionTemplate;

    public BarLineContext(MetaProjectPO project, BarLinePO barLine) {
        super(project, barLine);
        this.axisX = barLine.getAxisX();
        this.axisX2 = barLine.getAxisX2();
        this.axisYList = barLine.getAxisYList();
        this.optionTemplate = barLine.getOptionTemplate();
    }

    public ChartItemDTO<DimensionPO> getAxisX() {
        return axisX;
    }

    public ChartItemDTO<DimensionPO> getAxisX2() {
        return axisX2;
    }

    public List<ChartItemDTO<MetricsPO>> getAxisYList() {
        return axisYList;
    }

    public String getOptionTemplate() {
        return optionTemplate;
    }

}
