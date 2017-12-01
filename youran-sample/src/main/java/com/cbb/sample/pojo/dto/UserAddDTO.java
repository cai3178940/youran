package com.cbb.sample.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.cbb.sample.constant.Sex;
import com.youran.common.constant.JsonFieldConst;
import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.cbb.sample.pojo.example.UserExample.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 14:53
 */
@ApiModel(description = "新增【用户】的参数")
public class UserAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_USERNAME,example = E_USERNAME)
    @NotNull
    @Length(max = 40,message = "userName最大长度不能超过{max}")
    private String userName;


    @ApiModelProperty(notes = N_SEX,example = E_SEX)
    @NotNull
    @Const(constClass = Sex.class)
    private Integer sex;


    @ApiModelProperty(notes = N_BIRTHDAY,example = E_BIRTHDAY)
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    private Date birthday;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }



}
