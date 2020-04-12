package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.*;

/**
 * 图表上下文构造器
 *
 * @author: cbb
 * @date: 2020-04-12
 */
public class ChartContextBuilder {

    /**
     * 构造图表上下文
     *
     * @param project
     * @param chart
     * @return
     */
    public static AbstractChartContext build(MetaProjectPO project, MetaChartPO chart) {
        if (chart instanceof AggTablePO) {
            return new AggTableContext(project, ((AggTablePO) chart));
        }
        if (chart instanceof BarLinePO) {
            return new BarLineContext(project, ((BarLinePO) chart));
        }
        if (chart instanceof DetailListPO) {
            return new DetailListContext(project, ((DetailListPO) chart));
        }
        if (chart instanceof PiePO) {
            return new PieContext(project, ((PiePO) chart));
        }
        return null;
    }

}
