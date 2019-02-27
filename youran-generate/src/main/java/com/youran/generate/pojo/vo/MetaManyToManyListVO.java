package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.youran.generate.pojo.example.MetaManyToManyExample.*;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/7/4
 */
public class MetaManyToManyListVO extends AbstractVO {

    @ApiModelProperty(notes = N_MTMID, example = E_MTMID)
    private Integer mtmId;

    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    private Integer projectId;
    @ApiModelProperty(notes = N_TABLENAME, example = E_TABLENAME)
    private String tableName;

    @ApiModelProperty(notes = N_SCHEMANAME, example = E_SCHEMANAME)
    private String schemaName;

    @ApiModelProperty(notes = N_DESC, example = E_DESC)
    private String desc;

    @ApiModelProperty(notes = N_ENTITYID1, example = E_ENTITYID1)
    private Integer entityId1;
    @ApiModelProperty(notes = N_ENTITYID2, example = E_ENTITYID2)
    private Integer entityId2;

    @ApiModelProperty(notes = N_HOLDREFER1, example = E_HOLDREFER1)
    private Integer holdRefer1;

    @ApiModelProperty(notes = N_HOLDREFER2, example = E_HOLDREFER2)
    private Integer holdRefer2;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getMtmId() {
        return mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEntityId1() {
        return entityId1;
    }

    public void setEntityId1(Integer entityId1) {
        this.entityId1 = entityId1;
    }

    public Integer getEntityId2() {
        return entityId2;
    }

    public void setEntityId2(Integer entityId2) {
        this.entityId2 = entityId2;
    }

    public Integer getHoldRefer1() {
        return holdRefer1;
    }

    public void setHoldRefer1(Integer holdRefer1) {
        this.holdRefer1 = holdRefer1;
    }

    public Integer getHoldRefer2() {
        return holdRefer2;
    }

    public void setHoldRefer2(Integer holdRefer2) {
        this.holdRefer2 = holdRefer2;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("mtmId", mtmId)
            .append("projectId", projectId)
            .append("tableName", tableName)
            .append("schemaName", schemaName)
            .append("desc", desc)
            .append("entityId1", entityId1)
            .append("entityId2", entityId2)
            .append("holdRefer1", holdRefer1)
            .append("holdRefer2", holdRefer2)
            .toString();
    }
}
