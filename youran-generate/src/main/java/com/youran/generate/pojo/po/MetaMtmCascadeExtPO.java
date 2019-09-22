package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreatedOperatedDeletedVersion;

import java.util.Date;

/**
 * <p>Title: 多对多级联扩展</p>
 * <p>Description: 多对多级联扩展</p>
 * @author cbb
 * @date 2019/09/21
 */
public class MetaMtmCascadeExtPO extends AbstractPO implements CreatedOperatedDeletedVersion {

    /**
     * 主键ID
     */
    private Integer cascadeMtmExtId;

    /**
     * 多对多id
     */
    private Integer mtmId;

    /**
     * 实体id
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
     * 创建时间【yyyy-MM-dd HH:mm:ss】
     */
    private Date createdTime;

    /**
     * 创建人【最大长度20】
     */
    private String createdBy;

    /**
     * 修改时间【yyyy-MM-dd HH:mm:ss】
     */
    private Date operatedTime;

    /**
     * 修改人【最大长度20】
     */
    private String operatedBy;

    /**
     * 乐观锁版本号【整型】
     */
    private Integer version;

    /**
     * 逻辑删除标识【0-未删除，1-已删除】
     */
    private Boolean deleted;


    public Integer getCascadeMtmExtId() {
        return this.cascadeMtmExtId;
    }

    public void setCascadeMtmExtId(Integer cascadeMtmExtId) {
        this.cascadeMtmExtId = cascadeMtmExtId;
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

    @Override
    public Date getCreatedTime() {
        return this.createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getOperatedTime() {
        return this.operatedTime;
    }

    @Override
    public void setOperatedTime(Date operatedTime) {
        this.operatedTime = operatedTime;
    }

    @Override
    public String getOperatedBy() {
        return this.operatedBy;
    }

    @Override
    public void setOperatedBy(String operatedBy) {
        this.operatedBy = operatedBy;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Boolean getDeleted() {
        return this.deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }



}

