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
 * Title:新增元数据项目DTO
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:05
 */
@ApiModel(description = "新增元数据项目参数")
public class MetaProjectAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_PACKAGENAME, example = E_PACKAGENAME)
    @NotNull
    @Length(min=1, max = 100, message = "packageName最大长度不能超过100")
    private String packageName;

    @ApiModelProperty(notes = N_PROJECTNAME, example = E_PROJECTNAME)
    @NotNull
    @Length(min=1, max = 50, message = "projectName最大长度不能超过50")
    private String projectName;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    @Length(min=1, max = 50, message = "author最大长度不能超过50")
    private String author;

    @ApiModelProperty(notes = N_REMOTE, example = E_REMOTE)
    @NotNull
    @Const(constClass = BoolConst.class)
    private Integer remote;

    @ApiModelProperty(notes = N_REMOTEURL, example = E_REMOTEURL)
    @Length(min=1, max = 256, message = "remoteUrl最大长度不能超过256")
    private String remoteUrl;

    @ApiModelProperty(notes = N_USERNAME, example = E_USERNAME)
    @Length(min=1, max = 32, message = "username最大长度不能超过32")
    private String username;

    @ApiModelProperty(notes = N_PASSWORD, example = E_PASSWORD)
    @Length(min=1, max = 32, message = "password最大长度不能超过32")
    private String password;

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
}
