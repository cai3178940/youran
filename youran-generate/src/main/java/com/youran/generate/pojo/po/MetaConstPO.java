package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Title:常量类
 * Description:
 * Author: cbb
 * Create Time:2017/6/13 15:25
 */
public class MetaConstPO extends AbstractPO implements CreateOperateDeleteVersion {

    /**
     * 常量id
     */
    private Integer constId;

    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 常量名
     */
    private String constName;

    /**
     * 常量备注
     */
    private String constRemark;

    /**
     * 常量值字段类型：整型或字符串
     */
    private Integer constType;

    /**
     * 常量值列表
     */
    private List<MetaConstDetailPO> detailList;

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

    public void addDetail(MetaConstDetailPO metaConstDetailPO){
        if(detailList==null){
            detailList = new ArrayList<>();
        }
        detailList.add(metaConstDetailPO);
    }

    public List<MetaConstDetailPO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<MetaConstDetailPO> detailList) {
        this.detailList = detailList;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getConstType() {
        return constType;
    }

    public void setConstType(Integer constType) {
        this.constType = constType;
    }

    public String getConstRemark() {
        return constRemark;
    }

    public void setConstRemark(String constRemark) {
        this.constRemark = constRemark;
    }

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDelSign() {
        return delSign;
    }

    public void setDelSign(Integer delSign) {
        this.delSign = delSign;
    }
}
