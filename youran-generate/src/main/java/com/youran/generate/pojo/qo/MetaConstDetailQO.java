package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaConstDetailExample.E_CONSTID;
import static com.youran.generate.pojo.example.MetaConstDetailExample.N_CONSTID;


/**
 * Title: 查询参数
 * Description:
 * Author: cbb
 * Create Time:2017/6/14 9:32
 */
public class MetaConstDetailQO extends AbstractQO {

    @ApiParam(value = N_CONSTID, example = E_CONSTID)
    @NotNull
    private Integer constId;

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
    }
}
