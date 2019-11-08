package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.UserSettingExample.*;

/**
 * 修改【用户配置】的参数
 *
 * @author cbb
 * @date 2019/11/08
 */
@ApiModel(description = "修改【用户配置】的参数")
public class UserSettingUpdateDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_ID,example = E_ID,required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(hidden = true)
    private String username;

    @ApiModelProperty(notes = N_TEMPLATE_ENABLED,example = E_TEMPLATE_ENABLED,required = true)
    @NotNull
    private Boolean templateEnabled;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

