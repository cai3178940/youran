package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaFieldExample.*;

/**
 * <p>Title:修改字段DTO</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@ApiModel(description = "修改字段参数")
public class MetaFieldUpdateDTO extends MetaFieldAddDTO {

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
