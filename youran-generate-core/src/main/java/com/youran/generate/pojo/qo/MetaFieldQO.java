package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaFieldExample.E_ENTITYID;
import static com.youran.generate.pojo.example.MetaFieldExample.N_ENTITYID;

/**
 * 查询参数
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaFieldQO extends AbstractQO {

    @ApiParam(value = N_ENTITYID, example = E_ENTITYID)
    @NotNull
    private Integer entityId;

    @ApiParam(value = "是否携带级联字段数量", example = "true")
    private Boolean withCascadeFieldNum;

    public Boolean getWithCascadeFieldNum() {
        return withCascadeFieldNum;
    }

    public void setWithCascadeFieldNum(Boolean withCascadeFieldNum) {
        this.withCascadeFieldNum = withCascadeFieldNum;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
