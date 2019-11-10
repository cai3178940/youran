package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.youran.generate.pojo.example.MetaManyToManyExample.*;

/**
 * 多对多列表VO
 *
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
    private Boolean holdRefer1;

    @ApiModelProperty(notes = N_HOLDREFER2, example = E_HOLDREFER2)
    private Boolean holdRefer2;

    /**
     * 实体A对应多对多关联表的id字段名
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_ENTITYIDFIELD1, example = E_ENTITYIDFIELD1)
    private String entityIdField1;

    /**
     * 实体B对应多对多关联表的id字段名
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_ENTITYIDFIELD2, example = E_ENTITYIDFIELD2)
    private String entityIdField2;

    /**
     * 是否需要自增id字段
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_NEEDID, example = E_NEEDID)
    private Boolean needId;
    /**
     * id字段是否bigint
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_BIGID, example = E_BIGID)
    private Boolean bigId;


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

    public Boolean getHoldRefer1() {
        return holdRefer1;
    }

    public void setHoldRefer1(Boolean holdRefer1) {
        this.holdRefer1 = holdRefer1;
    }

    public Boolean getHoldRefer2() {
        return holdRefer2;
    }

    public void setHoldRefer2(Boolean holdRefer2) {
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

    public String getEntityIdField1() {
        return entityIdField1;
    }

    public void setEntityIdField1(String entityIdField1) {
        this.entityIdField1 = entityIdField1;
    }

    public String getEntityIdField2() {
        return entityIdField2;
    }

    public void setEntityIdField2(String entityIdField2) {
        this.entityIdField2 = entityIdField2;
    }

    public Boolean getNeedId() {
        return needId;
    }

    public void setNeedId(Boolean needId) {
        this.needId = needId;
    }

    public Boolean getBigId() {
        return bigId;
    }

    public void setBigId(Boolean bigId) {
        this.bigId = bigId;
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
            .append("entityIdField1", entityIdField1)
            .append("entityIdField2", entityIdField2)
            .append("needId", needId)
            .append("bigId", bigId)
            .toString();
    }
}
