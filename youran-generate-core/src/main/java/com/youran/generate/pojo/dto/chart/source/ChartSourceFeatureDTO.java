package com.youran.generate.pojo.dto.chart.source;

import java.util.List;

/**
 * 图表数据源特性
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class ChartSourceFeatureDTO {

    /**
     * 主实体id
     */
    private Integer entityId;

    /**
     * 关联
     */
    private List<JoinDTO> joins;

    /**
     * 数量限制
     */
    private Integer limit;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public List<JoinDTO> getJoins() {
        return joins;
    }

    public void setJoins(List<JoinDTO> joins) {
        this.joins = joins;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
