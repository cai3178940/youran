package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaFieldExample.E_ENTITYID;
import static com.youran.generate.pojo.example.MetaFieldExample.N_ENTITYID;

/**
 * Title: 查询参数
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 18:32
 */
public class MetaFieldQO extends AbstractQO {

    @ApiParam(value = N_ENTITYID, example = E_ENTITYID)
    @NotNull
    private Integer entityId;

    @ApiParam(value = "是否携带级联字段数量", example = "1")
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
