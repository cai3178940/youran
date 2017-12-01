package com.cbb.sample.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.Date;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 14:54
 */
public class UserPO extends AbstractPO implements CreateOperateDeleteVersion {

    private Integer id;

    private String userName;

    private Integer sex;

    private Date birthday;

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

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

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getOperateDate() {
        return operateDate;
    }

    @Override
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    @Override
    public String getOperateBy() {
        return operateBy;
    }

    @Override
    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Integer getDelSign() {
        return delSign;
    }

    @Override
    public void setDelSign(Integer delSign) {
        this.delSign = delSign;
    }
}
