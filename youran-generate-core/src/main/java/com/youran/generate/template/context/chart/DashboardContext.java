package com.youran.generate.template.context.chart;

import com.youran.generate.pojo.dto.chart.LayoutDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.template.context.BaseContext;

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
    private Integer dashboardId;

    /**
     * 名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 模块
     */
    private String module;

    /**
     * 特性
     */
    private String feature;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 图表布局
     */
    private List<LayoutDTO> layout;

    public DashboardContext(MetaProjectPO project, MetaDashboardPO metaDashboardPO) {
        super(project);
        this.metaDashboard = metaDashboardPO;
    }


    public MetaDashboardPO getMetaDashboard() {
        return metaDashboard;
    }

    public Integer getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(Integer dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<LayoutDTO> getLayout() {
        return layout;
    }

    public void setLayout(List<LayoutDTO> layout) {
        this.layout = layout;
    }
}
