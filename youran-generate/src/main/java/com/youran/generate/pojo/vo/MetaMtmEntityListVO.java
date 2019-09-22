package com.youran.generate.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.youran.generate.pojo.example.MetaManyToManyExample.E_MTMID;
import static com.youran.generate.pojo.example.MetaManyToManyExample.N_MTMID;

/**
 * <p>Title:实体列表展示对象</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaMtmEntityListVO extends MetaEntityListVO {

    @ApiModelProperty(notes = N_MTMID, example = E_MTMID)
    private Integer mtmId;

    @ApiModelProperty(notes = "级联字段数量", example = "0")
    private Integer cascadeFieldNum;

    public Integer getMtmId() {
        return mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getCascadeFieldNum() {
        return cascadeFieldNum;
    }

    public void setCascadeFieldNum(Integer cascadeFieldNum) {
        this.cascadeFieldNum = cascadeFieldNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("mtmId", mtmId)
            .append("cascadeFieldNum", cascadeFieldNum)
            .append("entityId", this.getEntityId())
            .append("projectId", this.getProjectId())
            .append("schemaName", this.getSchemaName())
            .append("className", this.getClassName())
            .append("tableName", this.getTableName())
            .append("title", this.getTitle())
            .append("desc", this.getDesc())
            .append("commonCall", this.getCommonCall())
            .append("pageSign", this.getPageSign())
            .toString();
    }
}
