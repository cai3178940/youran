package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.Date;

/**
 * Title: 级联扩展
 * Description: 从外键字段对应的表中级联查出要展示的字段
 * Author: cbb
 * Create Time: 2018/5/28 14:53
 */
public class MetaCascadeExtPO extends AbstractPO implements CreateOperateDeleteVersion {

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


    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;


    /**
     * 级联字段
     */
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

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getOperateDate() {
        return operateDate;
    }

    @Override
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    @Override
    public String getOperateBy() {
        return operateBy;
    }

    @Override
    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Integer getDelSign() {
        return delSign;
    }

    @Override
    public void setDelSign(Integer delSign) {
        this.delSign = delSign;
    }
}
