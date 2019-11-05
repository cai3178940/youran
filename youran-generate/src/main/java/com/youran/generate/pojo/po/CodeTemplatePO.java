package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 代码模板
 * <p> 代码模板
 *
 * @author cbb
 * @date 2019/10/24
 */
public class CodeTemplatePO extends BasePO {

    /**
     * 主键ID
     */
    private Integer templateId;
    /**
     * 模板编号
     */
    private String code;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板类型【1后端，2前端】
     *
     * @see com.youran.generate.constant.TemplateType
     */
    private Integer templateType;

    /**
     * 模板版本号
     */
    private String templateVersion;

    /**
     * 兼容最低系统版本号
     */
    private String sysLowVersion;

    /**
     * 是否系统默认模板
     */
    private Boolean sysDefault;

    /**
     * 备注【最大长度256】
     */
    private String remark;

    /**
     * 内部版本号，每次模板有变动都自动加一
     */
    @JsonIgnore
    private Integer innerVersion;

    /**
     * 模板文件列表
     */
    private List<TemplateFilePO> templateFiles;

    public List<TemplateFilePO> getTemplateFiles() {
        return templateFiles;
    }

    public void setTemplateFiles(List<TemplateFilePO> templateFiles) {
        this.templateFiles = templateFiles;
    }

    public Integer getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getTemplateVersion() {
        return this.templateVersion;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public String getSysLowVersion() {
        return this.sysLowVersion;
    }

    public void setSysLowVersion(String sysLowVersion) {
        this.sysLowVersion = sysLowVersion;
    }

    public Boolean getSysDefault() {
        return this.sysDefault;
    }

    public void setSysDefault(Boolean sysDefault) {
        this.sysDefault = sysDefault;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getInnerVersion() {
        return this.innerVersion;
    }

    public void setInnerVersion(Integer innerVersion) {
        this.innerVersion = innerVersion;
    }

}

