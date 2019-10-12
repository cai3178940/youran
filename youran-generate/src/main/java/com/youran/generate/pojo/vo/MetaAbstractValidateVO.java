package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Title:抽象校验结果展示对象</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2019/10/10
 */
public class MetaAbstractValidateVO extends AbstractVO {

    @ApiModelProperty(notes = "是否校验成功", example = "true")
    private Boolean success;

    public MetaAbstractValidateVO() {
        this.success = true;
    }

    public void error(){
        this.success = false;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
