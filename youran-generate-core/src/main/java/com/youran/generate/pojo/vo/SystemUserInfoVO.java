package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.UserSettingExample.*;
import static com.youran.generate.pojo.example.UserSettingExample.E_TEMPLATE_ENABLED;

/**
 * 系统及用户信息展示对象
 *
 * @author cbb
 * @date 2019/10/24
 */
public class SystemUserInfoVO extends AbstractVO {

    @ApiModelProperty(notes = N_ID,example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_TEMPLATE_ENABLED,example = E_TEMPLATE_ENABLED)
    private Boolean templateEnabled;

    @ApiModelProperty(notes = "当前用户", example = "admin")
    private String username;

    @ApiModelProperty(notes = "系统版本", example = "3.0.0")
    private String sysVersion;

    @ApiModelProperty(notes = "系统中存在模板", example = "false")
    private Boolean templateExists;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTemplateEnabled() {
        return templateEnabled;
    }

    public void setTemplateEnabled(Boolean templateEnabled) {
        this.templateEnabled = templateEnabled;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public Boolean getTemplateExists() {
        return templateExists;
    }

    public void setTemplateExists(Boolean templateExists) {
        this.templateExists = templateExists;
    }
}
