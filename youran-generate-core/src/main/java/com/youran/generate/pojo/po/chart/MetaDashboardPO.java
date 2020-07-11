package com.youran.generate.pojo.po.chart;

import com.youran.generate.pojo.po.BasePO;

/**
 * 看板
 *
 * @author cbb
 * @date 2020/06/13
 */
public class MetaDashboardPO extends BasePO {

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


    public Integer getDashboardId() {
        return this.dashboardId;
    }

    public void setDashboardId(Integer dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFeature() {
        return this.feature;
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
}

