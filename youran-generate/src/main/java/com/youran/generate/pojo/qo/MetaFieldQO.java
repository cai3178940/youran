package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaFieldExample.E_ENTITYID;
import static com.youran.generate.pojo.example.MetaFieldExample.N_ENTITYID;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 18:32
 */
@ApiModel(description = "查询参数")
public class MetaFieldQO extends AbstractQO {

    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    @NotNull
    private Integer entityId;

    @ApiModelProperty(notes = "是否携带级联字段数量", example = "1")
    private Integer withCascadeFieldNum;

    public Integer getWithCascadeFieldNum() {
        return withCascadeFieldNum;
    }

    public void setWithCascadeFieldNum(Integer withCascadeFieldNum) {
        this.withCascadeFieldNum = withCascadeFieldNum;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
