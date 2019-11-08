package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.UserSettingExample.*;

/**
 * 【用户配置】详情展示对象
 *
 * @author cbb
 * @date 2019/11/08
 */
@ApiModel(description = "【用户配置】详情展示对象")
public class UserSettingShowVO extends AbstractVO {

    @ApiModelProperty(notes = N_ID,example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_USERNAME,example = E_USERNAME)
    private String username;

    @ApiModelProperty(notes = N_TEMPLATE_ENABLED,example = E_TEMPLATE_ENABLED)
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

