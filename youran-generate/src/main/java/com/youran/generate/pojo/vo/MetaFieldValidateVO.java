package com.youran.generate.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaFieldExample.E_FIELDID;
import static com.youran.generate.pojo.example.MetaFieldExample.N_FIELDID;

/**
 * <p>Title:字段校验结果展示对象</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2019/10/10
 */
public class MetaFieldValidateVO extends MetaAbstractValidateVO {

    @ApiModelProperty(notes = N_FIELDID, example = E_FIELDID)
    private Integer fieldId;
    /**
     * 枚举校验存在
     */
    @ApiModelProperty(notes = "枚举校验存在", example = "true")
    private Boolean dicExistSuccess;
    /**
     * 不存在的枚举
     */
    @ApiModelProperty(notes = "不存在的枚举", example = "sex")
    private String dicNotExist;
    /**
     * 推荐的常量类型
     */
    @ApiModelProperty(notes = "推荐的常量类型【1-整数，2-字符串】", example = "1")
    private Integer suggestConstType;
    /**
     * 推荐的常量描述
     */
    @ApiModelProperty(notes = "推荐的常量描述", example = "性别")
    private String suggestConstRemark;

    public MetaFieldValidateVO() {
        this.dicExistSuccess = true;
    }

    /**
     * 设置枚举不存在
     * @param dic
     */
    public void dicNotExist(String dic){
        this.error();
        this.dicExistSuccess = false;
        this.dicNotExist = dic;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Boolean getDicExistSuccess() {
        return dicExistSuccess;
    }

    public void setDicExistSuccess(Boolean dicExistSuccess) {
        this.dicExistSuccess = dicExistSuccess;
    }

    public String getDicNotExist() {
        return dicNotExist;
    }

    public void setDicNotExist(String dicNotExist) {
        this.dicNotExist = dicNotExist;
    }

    public Integer getSuggestConstType() {
        return suggestConstType;
    }

    public void setSuggestConstType(Integer suggestConstType) {
        this.suggestConstType = suggestConstType;
    }

    public String getSuggestConstRemark() {
        return suggestConstRemark;
    }

    public void setSuggestConstRemark(String suggestConstRemark) {
        this.suggestConstRemark = suggestConstRemark;
    }
}
