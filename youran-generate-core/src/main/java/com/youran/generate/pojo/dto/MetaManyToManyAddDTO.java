package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaManyToManyExample.*;

/**
 * 新增多对多关系参数
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@ApiModel(description = "新增多对多关系参数")
public class MetaManyToManyAddDTO extends AbstractDTO {


    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    @NotNull
    private Integer projectId;

    @ApiModelProperty(notes = N_TABLENAME, example = E_TABLENAME)
    @NotNull
    @Length(max = 50, message = "tableName最大长度不能超过{max}")
    private String tableName;

    @ApiModelProperty(notes = N_SCHEMANAME, example = E_SCHEMANAME)
    @Length(max = 20, message = "schemaName最大长度不能超过{max}")
    private String schemaName;

    @ApiModelProperty(notes = N_DESC, example = E_DESC)
    @Length(max = 250, message = "desc最大长度不能超过{max}")
    private String desc;

    @ApiModelProperty(notes = N_ENTITYID1, example = E_ENTITYID1)
    @NotNull
    private Integer entityId1;
    @ApiModelProperty(notes = N_ENTITYID2, example = E_ENTITYID2)
    @NotNull
    private Integer entityId2;

    @ApiModelProperty(notes = N_HOLDREFER1, example = E_HOLDREFER1)
    @NotNull
    private Boolean holdRefer1;

    @ApiModelProperty(notes = N_HOLDREFER2, example = E_HOLDREFER2)
    @NotNull
    private Boolean holdRefer2;

    /**
     * 实体A对应多对多关联表的id字段名
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_ENTITYIDFIELD1, example = E_ENTITYIDFIELD1)
    @Length(max = 64, message = "entityIdField1最大长度不能超过{max}")
    private String entityIdField1;

    /**
     * 实体B对应多对多关联表的id字段名
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_ENTITYIDFIELD2, example = E_ENTITYIDFIELD2)
    @Length(max = 64, message = "entityIdField2最大长度不能超过{max}")
    private String entityIdField2;

    /**
     * 是否需要自增id字段
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_NEEDID, example = E_NEEDID)
    @NotNull
    private Boolean needId;
    /**
     * id字段是否bigint
     * 2019-08-07新增
     */
    @ApiModelProperty(notes = N_BIGID, example = E_BIGID)
    @NotNull
    private Boolean bigId;

    /**
     * 多对多特性
     */
    private MetaMtmFeatureDTO feature;

    public MetaMtmFeatureDTO getFeature() {
        return feature;
    }

    public void setFeature(MetaMtmFeatureDTO feature) {
        this.feature = feature;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
}
