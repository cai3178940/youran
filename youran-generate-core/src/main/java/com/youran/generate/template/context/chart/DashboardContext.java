package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.dto.chart.LayoutDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.template.context.BaseContext;
import com.youran.generate.util.SwitchCaseUtil;

import java.util.List;

/**
 * @author: cbb
 * @date: 2020-07-17
 */
public class DashboardContext extends BaseContext {

    private final MetaDashboardPO metaDashboard;

    /**
     * 主键ID
     */
    private final Integer dashboardId;

    /**
     * 名称
     */
    private final String name;

    /**
     * 名称-首个单词转小写
     */
    private final String nameLower;

    /**
     * 标题
     */
    private final String title;

    /**
     * 模块
     */
    private final String module;

    /**
     * 项目id
     */
    private final Integer projectId;

    /**
     * 图表布局
     */
    private final List<LayoutDTO> layout;

    public DashboardContext(MetaProjectPO project, MetaDashboardPO metaDashboard) {
        super(project);
        this.metaDashboard = metaDashboard;
        this.dashboardId = metaDashboard.getDashboardId();
        this.name = metaDashboard.getName();
        this.nameLower = SwitchCaseUtil.lowerFirstWord(metaDashboard.getName());
        this.title = metaDashboard.getTitle();
        this.module = metaDashboard.getModule();
        this.projectId = metaDashboard.getProjectId();
        this.layout = metaDashboard.getLayout();
    }


    public String getNameLower() {
        return nameLower;
    }

    public MetaDashboardPO getMetaDashboard() {
        return metaDashboard;
    }

    public Integer getDashboardId() {
        return dashboardId;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getModule() {
        return module;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public List<LayoutDTO> getLayout() {
        return layout;
    }

}
