package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import com.youran.generate.constant.PatternConst;
import com.youran.generate.constant.TemplateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.youran.generate.pojo.example.CodeTemplateExample.*;

/**
 * <p>Title: 新增【代码模板】的参数</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
@ApiModel(description = "新增【代码模板】的参数")
public class CodeTemplateAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_NAME, example = E_NAME, required = true)
    @NotNull
    @Length(max = 32)
    private String name;

    @ApiModelProperty(notes = N_TEMPLATE_TYPE, example = E_TEMPLATE_TYPE, required = true, allowableValues = TemplateType.VALUES_STR)
    @NotNull
    @Const(constClass = TemplateType.class)
    private Integer templateType;

    @ApiModelProperty(notes = N_TEMPLATE_VERSION, example = E_TEMPLATE_VERSION, required = true)
    @NotNull
    @Length(max = 10)
    @Pattern(regexp = PatternConst.VERSION, message = PatternConst.VERSION_MSG)
    private String templateVersion;

    @ApiModelProperty(notes = N_SYS_LOW_VERSION, example = E_SYS_LOW_VERSION, required = true)
    @NotNull
    @Length(max = 10)
    @Pattern(regexp = PatternConst.VERSION, message = PatternConst.VERSION_MSG)
    private String sysLowVersion;

    @ApiModelProperty(notes = N_REMARK, example = E_REMARK)
    @Length(max = 256)
    private String remark;


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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}


