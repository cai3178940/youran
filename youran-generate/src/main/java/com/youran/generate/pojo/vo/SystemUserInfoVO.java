package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Title: 系统及用户信息展示对象</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
public class SystemUserInfoVO extends AbstractVO {

    @ApiModelProperty(notes = "当前用户", example = "admin")
    private String user;

    @ApiModelProperty(notes = "系统版本", example = "2.2.0")
    private String sysVersion;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }
}
