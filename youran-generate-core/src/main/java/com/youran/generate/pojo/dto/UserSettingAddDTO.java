package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.UserSettingExample.*;

/**
 * 新增【用户配置】的参数
 *
 * @author cbb
 * @date 2019/11/08
 */
@ApiModel(description = "新增【用户配置】的参数")
public class UserSettingAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_USERNAME, example = E_USERNAME, required = true)
    @NotNull
    @Length(max = 32)
    private String username;

    @ApiModelProperty(notes = N_TEMPLATE_ENABLED, example = E_TEMPLATE_ENABLED, required = true)
    @NotNull
    private Boolean templateEnabled;


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getTemplateEnabled() {
        return this.templateEnabled;
    }

    public void setTemplateEnabled(Boolean templateEnabled) {
        this.templateEnabled = templateEnabled;
    }

}


