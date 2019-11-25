package com.youran.generate.pojo.po;

/**
 * @author: cbb
 * @date: 2018/3/17
 */
public class GenHistoryPO extends BasePO {

    private Integer historyId;

    private Integer projectId;

    private String remoteUrl;

    private String commit;

    private String branch;

    private String sysVersion;

    private Integer projectVersion;

    private Integer templateId;

    private Integer templateInnerVersion;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateInnerVersion() {
        return templateInnerVersion;
    }

    public void setTemplateInnerVersion(Integer templateInnerVersion) {
        this.templateInnerVersion = templateInnerVersion;
    }

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

}
