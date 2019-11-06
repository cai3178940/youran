package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.CodeTemplateExample.E_TEMPLATE_ID;
import static com.youran.generate.pojo.example.CodeTemplateExample.N_TEMPLATE_ID;

/**
 * 修改【代码模板】的参数
 *
 * @author cbb
 * @date 2019/10/24
 */
@ApiModel(description = "修改【代码模板】的参数")
public class CodeTemplateUpdateDTO extends CodeTemplateAddDTO {

    @ApiModelProperty(notes = N_TEMPLATE_ID, example = E_TEMPLATE_ID, required = true)
    @NotNull
    private Integer templateId;

    public Integer getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

}

