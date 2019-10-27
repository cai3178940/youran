package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import com.youran.generate.constant.TemplateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.CodeTemplateExample.*;

/**
 * <p>Title: 【代码模板】列表展示对象</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
@ApiModel(description = "【代码模板】列表展示对象")
public class CodeTemplateListVO extends AbstractVO {

    @ApiModelProperty(notes = N_TEMPLATE_ID,example = E_TEMPLATE_ID)
    private Integer templateId;

    @ApiModelProperty(notes = N_NAME,example = E_NAME)
    private String name;

    @ApiModelProperty(notes = N_TEMPLATE_TYPE,example = E_TEMPLATE_TYPE, allowableValues = TemplateType.VALUES_STR)
    private Integer templateType;

    @ApiModelProperty(notes = N_TEMPLATE_VERSION,example = E_TEMPLATE_VERSION)
    private String templateVersion;

    @ApiModelProperty(notes = N_SYS_LOW_VERSION,example = E_SYS_LOW_VERSION)
    private String sysLowVersion;

    @ApiModelProperty(notes = N_SYS_DEFAULT,example = E_SYS_DEFAULT)
    private Boolean sysDefault;

    @ApiModelProperty(notes = N_REMARK,example = E_REMARK)
    private String remark;

    @ApiModelProperty(notes = N_FROM_TEMPLATE_ID,example = E_FROM_TEMPLATE_ID)
    private Integer fromTemplateId;


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



}

