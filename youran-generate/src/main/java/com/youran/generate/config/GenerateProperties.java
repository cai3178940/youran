package com.youran.generate.config;

import com.youran.generate.constant.DevMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title:配置参数类</p>
 * <p>Description:该类中的参数来自配置文件</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Component
@ConfigurationProperties(prefix = "youran")
public class GenerateProperties {

    /**
     * 是否启用web服务
     */
    private Boolean enable;

    /**
     * 开发模式：0非开发模式，1本地工程全替换，2本地工程部分替换
     * @see DevMode
     */
    private Integer devMode = 0;

    /**
     * 是否删除临时代码文件
     */
    private boolean delTemp = true;

    /**
     * 本地开发工程路径
     */
    private String devProjectDir;

    /**
     * 系统版本
     */
    private String version;
    /**
     * aes对称加密Key
     */
    private String aesKey;

    /**
     * 是否启用登录校验
     */
    private boolean securityEnabled;

    public boolean isSecurityEnabled() {
        return securityEnabled;
    }

    public void setSecurityEnabled(boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getDevMode() {
        return devMode;
    }

    public void setDevMode(Integer devMode) {
        this.devMode = devMode;
    }

    public boolean isDelTemp() {
        return delTemp;
    }

    public void setDelTemp(boolean delTemp) {
        this.delTemp = delTemp;
    }

    public String getDevProjectDir() {
        return devProjectDir;
    }

    public void setDevProjectDir(String devProjectDir) {
        this.devProjectDir = devProjectDir;
    }



}
