package com.youran.generate.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaConstDetailExample.*;


/**
 * Title:元数据常量值列表展示对象
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 17:54
 */
public class MetaConstDetailListVO {

    @ApiModelProperty(notes = N_CONSTDETAILID, example = E_CONSTDETAILID)
    private Integer constDetailId;

    @ApiModelProperty(notes = N_CONSTID, example = E_CONSTID)
    private Integer constId;

    @ApiModelProperty(notes = N_DETAILNAME,example = E_DETAILNAME)
    private String detailName;

    @ApiModelProperty(notes = N_DETAILVALUE,example = E_DETAILVALUE)
    private String detailValue;

    @ApiModelProperty(notes = N_DETAILREMARK,example = E_DETAILREMARK)
    private String detailRemark;

    public Integer getConstDetailId() {
        return constDetailId;
    }

    public void setConstDetailId(Integer constDetailId) {
        this.constDetailId = constDetailId;
    }

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
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

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }
}
