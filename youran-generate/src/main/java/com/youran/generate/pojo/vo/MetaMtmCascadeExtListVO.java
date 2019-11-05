package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaMtmCascadeExtExample.*;

/**
 * 【多对多级联扩展】列表展示对象
 *
 * @author cbb
 * @date 2019/09/21
 */
@ApiModel(description = "【多对多级联扩展】列表展示对象")
public class MetaMtmCascadeExtListVO extends AbstractVO {

    @ApiModelProperty(notes = N_MTM_CASCADE_EXT_ID, example = E_MTM_CASCADE_EXT_ID)
    private Integer mtmCascadeExtId;

    @ApiModelProperty(notes = N_MTM_ID, example = E_MTM_ID)
    private Integer mtmId;

    @ApiModelProperty(notes = N_ENTITY_ID, example = E_ENTITY_ID)
    private Integer entityId;

    @ApiModelProperty(notes = N_CASCADE_ENTITY_ID, example = E_CASCADE_ENTITY_ID)
    private Integer cascadeEntityId;

    @ApiModelProperty(notes = N_CASCADE_FIELD_ID, example = E_CASCADE_FIELD_ID)
    private Integer cascadeFieldId;

    @ApiModelProperty(notes = N_ALIAS, example = E_ALIAS)
    private String alias;

    @ApiModelProperty(notes = N_LIST, example = E_LIST)
    private Integer list;

    @ApiModelProperty(notes = N_SHOW, example = E_SHOW)
    private Integer show;

    @ApiModelProperty(notes = N_QUERY, example = E_QUERY)
    private Integer query;

    /**
     * 级联展示字段名
     */
    @ApiModelProperty(notes = "级联展示字段名", example = "name")
    private String cascadeJfieldName;

    /**
     * 级联展示字段描述
     */
    @ApiModelProperty(notes = "级联展示字段描述", example = "名称")
    private String cascadeFieldDesc;

    public String getCascadeJfieldName() {
        return cascadeJfieldName;
    }

    public void setCascadeJfieldName(String cascadeJfieldName) {
        this.cascadeJfieldName = cascadeJfieldName;
    }

    public String getCascadeFieldDesc() {
        return cascadeFieldDesc;
    }

    public void setCascadeFieldDesc(String cascadeFieldDesc) {
        this.cascadeFieldDesc = cascadeFieldDesc;
    }

    public Integer getMtmCascadeExtId() {
        return this.mtmCascadeExtId;
    }

    public void setMtmCascadeExtId(Integer mtmCascadeExtId) {
        this.mtmCascadeExtId = mtmCascadeExtId;
    }

    public Integer getMtmId() {
        return this.mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getCascadeEntityId() {
        return this.cascadeEntityId;
    }

    public void setCascadeEntityId(Integer cascadeEntityId) {
        this.cascadeEntityId = cascadeEntityId;
    }

    public Integer getCascadeFieldId() {
        return this.cascadeFieldId;
    }

    public void setCascadeFieldId(Integer cascadeFieldId) {
        this.cascadeFieldId = cascadeFieldId;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getList() {
        return this.list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getShow() {
        return this.show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getQuery() {
        return this.query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }


}

