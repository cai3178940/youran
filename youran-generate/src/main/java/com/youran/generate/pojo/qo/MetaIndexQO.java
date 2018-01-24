package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaIndexExample.E_ENTITYID;
import static com.youran.generate.pojo.example.MetaIndexExample.N_ENTITYID;


/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 18:32
 */
@ApiModel(description = "查询参数")
public class MetaIndexQO extends AbstractQO {

    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    @NotNull
    private Integer entityId;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
