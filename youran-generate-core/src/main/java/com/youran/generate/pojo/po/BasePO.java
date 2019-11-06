package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreatedOperatedDeletedVersion;

import java.util.Date;

/**
 * 常规抽象PO
 *
 * @author: cbb
 * @date: 7/21/2018
 */
public abstract class BasePO extends AbstractPO implements CreatedOperatedDeletedVersion {

    @JsonIgnore
    private Date createdTime;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private Date operatedTime;

    @JsonIgnore
    private String operatedBy;

    @JsonIgnore
    private Integer version;

    @JsonIgnore
    private Boolean deleted;

    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getOperatedTime() {
        return operatedTime;
    }

    @Override
    public void setOperatedTime(Date operatedTime) {
        this.operatedTime = operatedTime;
    }

    @Override
    public String getOperatedBy() {
        return operatedBy;
    }

    @Override
    public void setOperatedBy(String operatedBy) {
        this.operatedBy = operatedBy;
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
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
