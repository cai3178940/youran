package com.cbb.sample.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.youran.common.constant.JsonFieldConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import static com.cbb.sample.pojo.example.UserExample.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 14:53
 */
@ApiModel(description = "【用户】详情展示对象")
public class UserShowVO {

    @ApiModelProperty(notes = N_ID,example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_USERNAME,example = E_USERNAME)
    private String userName;

    @ApiModelProperty(notes = N_SEX,example = E_SEX)
    private Integer sex;

    @ApiModelProperty(notes = N_BIRTHDAY,example = E_BIRTHDAY)
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    private Date birthday;

    @ApiModelProperty(notes = N_CREATEDATE,example = E_CREATEDATE)
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    private Date createDate;

    @ApiModelProperty(notes = N_CREATEBY,example = E_CREATEBY)
    private String createBy;

    @ApiModelProperty(notes = N_OPERATEDATE,example = E_OPERATEDATE)
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    private Date operateDate;

    @ApiModelProperty(notes = N_OPERATEBY,example = E_OPERATEBY)
    private String operateBy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperateBy() {
        return operateBy;
    }

    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy;
    }


}
