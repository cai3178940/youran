package com.youran.generate.pojo.po;

/**
 * 常量值
 *
 * @author: cbb
 * @date: 2017/6/13
 */
public class MetaConstDetailPO extends BasePO {


    /**
     * 常量值id
     */
    private Integer constDetailId;
    /**
     * 所属项目id
     */
    private Integer projectId;
    /**
     * 常量id
     */
    private Integer constId;
    /**
     * 常量字段名称
     */
    private String detailName;
    /**
     * 常量值数值
     */
    private String detailValue;
    /**
     * 常量值备注
     */
    private String detailRemark;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public Integer getConstDetailId() {
        return constDetailId;
    }

    public void setConstDetailId(Integer constDetailId) {
        this.constDetailId = constDetailId;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
    }

}
