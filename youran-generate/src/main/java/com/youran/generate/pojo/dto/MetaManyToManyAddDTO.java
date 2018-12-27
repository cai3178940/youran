package com.youran.generate.pojo.dto;

import com.youran.common.constant.BoolConst;
import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaManyToManyExample.*;

/**
 * Title:新增实体DTO
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:05
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
    @Const(constClass = BoolConst.class)
    private Integer holdRefer1;

    @ApiModelProperty(notes = N_HOLDREFER2, example = E_HOLDREFER2)
    @NotNull
    @Const(constClass = BoolConst.class)
    private Integer holdRefer2;

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

    public Integer getHoldRefer2() {
        return holdRefer2;
    }

    public void setHoldRefer2(Integer holdRefer2) {
        this.holdRefer2 = holdRefer2;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
