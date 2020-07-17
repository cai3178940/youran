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
    private Integer dashboardId;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称-首个单词转小写
     */
    private String nameLower;

    /**
     * 标题
     */
    private String title;

    /**
     * 模块
     */
    private String module;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 图表布局
     */
    private List<LayoutDTO> layout;

    public DashboardContext(MetaProjectPO project, MetaDashboardPO metaDashboard) {
        super(project);
        this.metaDashboard = metaDashboard;
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
