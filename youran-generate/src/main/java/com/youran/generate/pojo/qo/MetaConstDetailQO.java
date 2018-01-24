package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaConstDetailExample.E_CONSTID;
import static com.youran.generate.pojo.example.MetaConstDetailExample.N_CONSTID;


/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/6/14 9:32
 */
@ApiModel(description = "查询参数")
public class MetaConstDetailQO extends AbstractQO {

    @ApiModelProperty(notes = N_CONSTID, example = E_CONSTID)
    @NotNull
    private Integer constId;

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
    }
}
