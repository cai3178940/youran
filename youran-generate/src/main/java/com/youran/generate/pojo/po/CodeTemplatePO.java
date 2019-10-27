package com.youran.generate.pojo.po;

/**
 * <p>Title: 代码模板</p>
 * <p>Description: 代码模板</p>
 * @author cbb
 * @date 2019/10/24
 */
public class CodeTemplatePO extends BasePO {

    /**
     * 主键ID
     */
    private Integer templateId;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板类型【1后端，2前端】
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
     * 来源模板id
     */
    private Integer fromTemplateId;

    /**
     * 内部版本号，每次模板有变动都自动加一
     */
    private Integer innerVersion;


    public Integer getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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

    public Integer getFromTemplateId() {
        return this.fromTemplateId;
    }

    public void setFromTemplateId(Integer fromTemplateId) {
        this.fromTemplateId = fromTemplateId;
    }

    public Integer getInnerVersion() {
        return this.innerVersion;
    }

    public void setInnerVersion(Integer innerVersion) {
        this.innerVersion = innerVersion;
    }

}

