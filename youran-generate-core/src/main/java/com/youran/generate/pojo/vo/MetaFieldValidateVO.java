package com.youran.generate.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.util.GuessUtil;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaFieldExample.E_FIELDID;
import static com.youran.generate.pojo.example.MetaFieldExample.N_FIELDID;

/**
 * 字段校验结果展示对象
 *
 * @author: cbb
 * @date: 2019/10/10
 */
public class MetaFieldValidateVO extends MetaAbstractValidateVO {

    /**
     * 当前字段
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private MetaFieldPO field;

    /**
     * 字段id
     */
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
    /**
     * java字段重名校验通过
     */
    @ApiModelProperty(notes = "java字段重名校验通过", example = "true")
    private Boolean sameJfieldNameSuccess;
    /**
     * mysql字段重名校验通过
     */
    @ApiModelProperty(notes = "mysql字段重名校验通过", example = "true")
    private Boolean sameFieldNameSuccess;
    /**
     * 标题候选字段等级
     */
    @ApiModelProperty(notes = "标题候选字段等级", example = "0")
    private Integer titleCandidate;


    public MetaFieldValidateVO(MetaFieldPO field) {
        this.field = field;
        this.fieldId = field.getFieldId();
        this.dicExistSuccess = true;
        this.sameJfieldNameSuccess = true;
        this.sameFieldNameSuccess = true;
        this.titleCandidate = 0;
    }

    /**
     * 设置枚举不存在错误
     */
    public void dicNotExistError() {
        String dic = this.field.getDicType();
        Integer constType = GuessUtil.guessConstType(JFieldType.find(this.field.getJfieldType()));
        this.error();
        this.dicExistSuccess = false;
        this.dicNotExist = dic;
        this.suggestConstType = constType;
        this.suggestConstRemark = this.field.getFieldDesc();
    }


    /**
     * 设置java字段名重复错误
     */
    public void sameJfieldNameError() {
        this.error();
        this.sameJfieldNameSuccess = false;
    }

    /**
     * 设置mysql字段名重复错误
     */
    public void sameFieldNameError() {
        this.error();
        this.sameFieldNameSuccess = false;
    }

    public MetaFieldPO getField() {
        return field;
    }

    public void setField(MetaFieldPO field) {
        this.field = field;
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

    public Boolean getSameJfieldNameSuccess() {
        return sameJfieldNameSuccess;
    }

    public void setSameJfieldNameSuccess(Boolean sameJfieldNameSuccess) {
        this.sameJfieldNameSuccess = sameJfieldNameSuccess;
    }

    public Boolean getSameFieldNameSuccess() {
        return sameFieldNameSuccess;
    }

    public void setSameFieldNameSuccess(Boolean sameFieldNameSuccess) {
        this.sameFieldNameSuccess = sameFieldNameSuccess;
    }

    public Integer getTitleCandidate() {
        return titleCandidate;
    }

    public void setTitleCandidate(Integer titleCandidate) {
        this.titleCandidate = titleCandidate;
    }
}
