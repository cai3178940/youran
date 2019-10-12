package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>Title:级联扩展</p>
 * <p>Description:从外键字段对应的表中级联查出要展示的字段</p>
 * @author: cbb
 * @date: 2018/5/28
 */
public class MetaCascadeExtPO extends BasePO {

    /**
     * 主键id
     */
    private Integer cascadeExtId;

    /**
     * 所属字段id
     */
    private Integer fieldId;

    /**
     * 所属实体id
     */
    private Integer entityId;

    /**
     * 展示字段别名
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
     * 是否为列表查询条件
     */
    private Integer query;

    /**
     * 级联实体的id
     */
    private Integer cascadeEntityId;

    /**
     * 级联展示字段的id
     */
    private Integer cascadeFieldId;

    /**
     * 级联字段
     */
    @JsonIgnore
    private MetaFieldPO cascadeField;

    public MetaFieldPO getCascadeField() {
        return cascadeField;
    }

    public void setCascadeField(MetaFieldPO cascadeField) {
        this.cascadeField = cascadeField;
    }

    public Integer getCascadeExtId() {
        return cascadeExtId;
    }

    public void setCascadeExtId(Integer cascadeExtId) {
        this.cascadeExtId = cascadeExtId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getQuery() {
        return query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }

    public Integer getCascadeEntityId() {
        return cascadeEntityId;
    }

    public void setCascadeEntityId(Integer cascadeEntityId) {
        this.cascadeEntityId = cascadeEntityId;
    }

    public Integer getCascadeFieldId() {
        return cascadeFieldId;
    }

    public void setCascadeFieldId(Integer cascadeFieldId) {
        this.cascadeFieldId = cascadeFieldId;
    }

}
