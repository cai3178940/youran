package com.youran.generate.pojo.dto;

import com.youran.common.constant.BoolConst;
import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaProjectExample.*;

/**
 * <p>Title:新增项目DTO</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@ApiModel(description = "新增项目参数")
public class MetaProjectAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_PACKAGENAME, example = E_PACKAGENAME)
    @NotNull
    @Length(min=1, max = 100, message = "packageName最大长度不能超过{max}")
    private String packageName;

    @ApiModelProperty(notes = N_PROJECTNAME, example = E_PROJECTNAME)
    @NotNull
    @Length(min=1, max = 50, message = "projectName最大长度不能超过{max}")
    private String projectName;

    @ApiModelProperty(notes = N_PROJECTDESC, example = E_PROJECTDESC)
    @NotNull
    @Length(min=1, max = 100, message = "projectDesc最大长度不能超过{max}")
    private String projectDesc;

    @ApiModelProperty(notes = N_GROUPID, example = E_GROUPID)
    @NotNull
    @Length(min=1, max = 50, message = "groupId最大长度不能超过{max}")
    private String groupId;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    @Length(min=1, max = 50, message = "author最大长度不能超过{max}")
    private String author;

    @ApiModelProperty(notes = N_REMOTE, example = E_REMOTE)
    @NotNull
    @Const(constClass = BoolConst.class)
    private Integer remote;

    @ApiModelProperty(notes = N_REMOTEURL, example = E_REMOTEURL)
    @Length(max = 256, message = "remoteUrl最大长度不能超过{max}")
    private String remoteUrl;

    @ApiModelProperty(notes = N_USERNAME, example = E_USERNAME)
    @Length(max = 32, message = "username最大长度不能超过{max}")
    private String username;

    @ApiModelProperty(notes = N_PASSWORD, example = E_PASSWORD)
    @Length(max = 32, message = "password最大长度不能超过{max}")
    private String password;

    /**
     * 项目特性
     */
    private MetaProjectFeatureDTO feature;

    public MetaProjectFeatureDTO getFeature() {
        return feature;
    }

    public void setFeature(MetaProjectFeatureDTO feature) {
        this.feature = feature;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }
}
