package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaCascadeExtExample.E_FIELDID;
import static com.youran.generate.pojo.example.MetaCascadeExtExample.N_FIELDID;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/6/14 9:32
 */
@ApiModel(description = "查询参数")
public class MetaCascadeExtQO extends AbstractQO {

    @ApiModelProperty(notes = N_FIELDID, example = E_FIELDID)
    @NotNull
    private Integer fieldId;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
}
