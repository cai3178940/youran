package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaMtmCascadeExtExample.*;

/**
 * <p>Title: 【多对多级联扩展】列表展示对象</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/09/21
 */
@ApiModel(description = "【多对多级联扩展】列表展示对象")
public class MetaMtmCascadeExtListVO extends AbstractVO {

    @ApiModelProperty(notes = N_CASCADE_MTM_EXT_ID,example = E_CASCADE_MTM_EXT_ID)
    private Integer cascadeMtmExtId;

    @ApiModelProperty(notes = N_MTM_ID,example = E_MTM_ID)
    private Integer mtmId;

    @ApiModelProperty(notes = N_ENTITY_ID,example = E_ENTITY_ID)
    private Integer entityId;

    @ApiModelProperty(notes = N_CASCADE_ENTITY_ID,example = E_CASCADE_ENTITY_ID)
    private Integer cascadeEntityId;

    @ApiModelProperty(notes = N_CASCADE_FIELD_ID,example = E_CASCADE_FIELD_ID)
    private Integer cascadeFieldId;

    @ApiModelProperty(notes = N_ALIAS,example = E_ALIAS)
    private String alias;

    @ApiModelProperty(notes = N_LIST,example = E_LIST)
    private Integer list;

    @ApiModelProperty(notes = N_SHOW,example = E_SHOW)
    private Integer show;

    @ApiModelProperty(notes = N_QUERY,example = E_QUERY)
    private Integer query;


    public Integer getCascadeMtmExtId() {
        return this.cascadeMtmExtId;
    }

    public void setCascadeMtmExtId(Integer cascadeMtmExtId) {
        this.cascadeMtmExtId = cascadeMtmExtId;
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

