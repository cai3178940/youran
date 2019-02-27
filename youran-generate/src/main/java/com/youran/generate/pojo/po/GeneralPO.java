package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreatedOperatedDeletedVersion;

import java.util.Date;

/**
 * <p>Title:常规抽象PO</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 7/21/2018
 */
public abstract class GeneralPO extends AbstractPO implements CreatedOperatedDeletedVersion {

    private Date createdTime;

    private String createdBy;

    private Date operatedTime;

    private String operatedBy;

    private Integer version;

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
