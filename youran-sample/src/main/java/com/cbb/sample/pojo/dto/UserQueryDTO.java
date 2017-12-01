
package com.cbb.sample.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.youran.common.constant.JsonFieldConst;
import com.youran.common.pojo.dto.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import static com.cbb.sample.pojo.example.UserExample.*;


/**
 * Title: 新增【用户】的参数
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */
@ApiModel(description = "新增【用户】的参数")
public class UserQueryDTO extends PageQueryDTO {

    @ApiModelProperty(notes = N_USERNAME,example = E_USERNAME)
    @Length(max = 20,message = "userName最大长度不能超过{max}")
    private String userName;

    @ApiModelProperty(notes = N_SEX,example = E_SEX)
    private Integer sex;

    @ApiModelProperty(notes = N_BIRTHDAY,example = E_BIRTHDAY)
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    private Date birthdayStart;

    @ApiModelProperty(notes = N_BIRTHDAY,example = E_BIRTHDAY)
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    private Date birthdayEnd;


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

    public Date getBirthdayStart() {
        return birthdayStart;
    }

    public void setBirthdayStart(Date birthdayStart) {
        this.birthdayStart = birthdayStart;
    }

    public Date getBirthdayEnd() {
        return birthdayEnd;
    }

    public void setBirthdayEnd(Date birthdayEnd) {
        this.birthdayEnd = birthdayEnd;
    }


}
