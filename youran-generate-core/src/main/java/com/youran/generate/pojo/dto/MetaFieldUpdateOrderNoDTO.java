package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaFieldExample.*;

/**
 * 修改字段序号
 *
 * @author cbb
 * @date 2019/9/21
 */
@ApiModel(description = "修改字段序号")
public class MetaFieldUpdateOrderNoDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_FIELDID, example = E_FIELDID)
    @NotNull
    private Integer fieldId;

    @ApiModelProperty(notes = N_ORDERNO, example = E_ORDERNO)
    @NotNull
    private Integer orderNo;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
