package com.youran.generate.pojo.po.chart.source;

import com.youran.generate.pojo.dto.chart.source.ChartSourceFeatureDTO;
import com.youran.generate.pojo.dto.chart.source.JoinDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import com.youran.generate.pojo.po.BasePO;

import java.util.List;

/**
 * 图表数据源
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartSourcePO extends BasePO {

    /**
     * 主键ID
     */
    private Integer sourceId;

    /**
     * 特性json
     */
    private String feature;

    /**
     * 是否聚合
     */
    private Boolean aggregation;


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

    /**
     * 反序列化特性json
     * 从json字符串中解析出dto信息
     */
    public void featureDeserialize() {
        ChartSourceFeatureDTO featureDTO = FeatureMapper.asChartSourceFeatureDTO(this.getFeature());
        this.entityId = featureDTO.getEntityId();
        this.joins = featureDTO.getJoins();
        this.limit = featureDTO.getLimit();
    }

    /**
     * 序列化特性json
     * 将dto信息序列化成json字符串
     */
    public void featureSerialize() {
        ChartSourceFeatureDTO featureDTO = new ChartSourceFeatureDTO();
        featureDTO.setEntityId(this.entityId);
        featureDTO.setJoins(this.joins);
        featureDTO.setLimit(this.limit);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Boolean getAggregation() {
        return this.aggregation;
    }

    public void setAggregation(Boolean aggregation) {
        this.aggregation = aggregation;
    }

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

