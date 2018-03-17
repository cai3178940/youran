package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.Date;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time: 2018/3/17 15:14
 */
public class GenHistoryPO extends AbstractPO implements CreateOperateDeleteVersion {

    private Integer historyId;

    private Integer projectId;

    private String remoteUrl;

    private String commit;

    private String branch;

    private String sysVersion;

    private Integer projectVersion;

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public Integer getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(Integer projectVersion) {
        this.projectVersion = projectVersion;
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
