package com.youran.generate.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Title:字段校验结果展示对象</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2019/10/10
 */
public class MetaFieldValidateVO extends MetaAbstractValidateVO {

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
     * 设置枚举不存在
     * @param dic
     */
    public void dicNotExist(String dic){
        this.error();
        this.dicExistSuccess = false;
        this.dicNotExist = dic;
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
}
