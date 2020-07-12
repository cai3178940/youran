package com.youran.generate.pojo.po.chart;

import com.youran.common.util.JsonUtil;
import com.youran.generate.pojo.dto.chart.LayoutDTO;
import com.youran.generate.pojo.po.BasePO;

import java.util.List;

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

    /**
     * 图表布局
     */
    private List<LayoutDTO> layout;


    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.layout = featureDTO.getLayout();
    }

    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setLayout(this.layout);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }

    public List<LayoutDTO> getLayout() {
        return layout;
    }

    public void setLayout(List<LayoutDTO> layout) {
        this.layout = layout;
    }

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

    static class FeatureDTO {
        private List<LayoutDTO> layout;

        public List<LayoutDTO> getLayout() {
            return layout;
        }

        public void setLayout(List<LayoutDTO> layout) {
            this.layout = layout;
        }
    }
}

