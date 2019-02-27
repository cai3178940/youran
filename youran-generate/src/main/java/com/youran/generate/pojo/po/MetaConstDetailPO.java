package com.youran.generate.pojo.po;

/**
 * <p>Title:常量值</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/6/13
 */
public class MetaConstDetailPO extends GeneralPO {


    /**
     * 常量值id
     */
    private Integer constDetailId;

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
