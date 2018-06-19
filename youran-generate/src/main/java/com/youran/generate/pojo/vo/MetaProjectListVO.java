package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaProjectExample.*;

/**
 * Title:项目列表展示对象
 * Description:
 * Author: cbb
 * Create Time:2017/5/24
 */
public class MetaProjectListVO extends AbstractVO {

    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    private Integer projectId;

    @ApiModelProperty(notes = N_PACKAGENAME, example = E_PACKAGENAME)
    private String packageName;

    @ApiModelProperty(notes = N_PROJECTNAME, example = E_PROJECTNAME)
    private String projectName;

    @ApiModelProperty(notes = N_GROUPID, example = E_GROUPID)
    private String groupId;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    private String author;

    @ApiModelProperty(notes = N_REMOTE, example = E_REMOTE)
    private Integer remote;

    @ApiModelProperty(notes = N_REMOTEURL, example = E_REMOTEURL)
    private String remoteUrl;

    @ApiModelProperty(notes = N_USERNAME, example = E_USERNAME)
    private String username;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getRemote() {
        return remote;
    }

    public void setRemote(Integer remote) {
        this.remote = remote;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
