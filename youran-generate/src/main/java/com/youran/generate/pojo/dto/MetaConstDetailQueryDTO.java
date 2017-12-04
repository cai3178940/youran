package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
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
public class MetaConstDetailQueryDTO extends AbstractDTO {

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
