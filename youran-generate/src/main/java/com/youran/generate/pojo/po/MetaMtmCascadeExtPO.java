package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 多对多级联扩展
 *
 * @author cbb
 * @date 2019/09/21
 */
public class MetaMtmCascadeExtPO extends BasePO {

    /**
     * 主键ID
     */
    private Integer mtmCascadeExtId;

    /**
     * 多对多id
     */
    private Integer mtmId;

    /**
     * 宿主实体id
     */
    private Integer entityId;

    /**
     * 级联所属实体id
     */
    private Integer cascadeEntityId;

    /**
     * 级联所属字段id
     */
    private Integer cascadeFieldId;

    /**
     * 级联查询字段别名
     */
    private String alias;

    /**
     * 是否在列表中展示
     */
    private Integer list;

    /**
     * 是否在详情中展示
     */
    private Integer show;

    /**
     * 是否为查询条件
     */
    private Integer query;

    /**
     * 宿主实体
     */
    @JsonIgnore
    private MetaEntityPO hostEntity;
    /**
     * 级联实体
     */
    @JsonIgnore
    private MetaEntityPO cascadeEntity;
    /**
     * 级联字段
     */
    @JsonIgnore
    private MetaFieldPO cascadeField;


    public Integer getMtmCascadeExtId() {
        return mtmCascadeExtId;
    }

    public void setMtmCascadeExtId(Integer mtmCascadeExtId) {
        this.mtmCascadeExtId = mtmCascadeExtId;
    }

    public Integer getMtmId() {
        return this.mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getCascadeEntityId() {
        return this.cascadeEntityId;
    }

    public void setCascadeEntityId(Integer cascadeEntityId) {
        this.cascadeEntityId = cascadeEntityId;
    }

    public Integer getCascadeFieldId() {
        return this.cascadeFieldId;
    }

    public void setCascadeFieldId(Integer cascadeFieldId) {
        this.cascadeFieldId = cascadeFieldId;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getList() {
        return this.list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getShow() {
        return this.show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getQuery() {
        return this.query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }

    public MetaEntityPO getCascadeEntity() {
        return cascadeEntity;
    }

    public void setCascadeEntity(MetaEntityPO cascadeEntity) {
        this.cascadeEntity = cascadeEntity;
    }

    public MetaFieldPO getCascadeField() {
        return cascadeField;
    }

    public void setCascadeField(MetaFieldPO cascadeField) {
        this.cascadeField = cascadeField;
    }

    public MetaEntityPO getHostEntity() {
        return hostEntity;
    }

    public void setHostEntity(MetaEntityPO hostEntity) {
        this.hostEntity = hostEntity;
    }


}

