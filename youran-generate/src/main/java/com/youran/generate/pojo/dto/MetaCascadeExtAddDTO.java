package com.youran.generate.pojo.dto;

import com.youran.common.constant.BoolConst;
import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaCascadeExtExample.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time: 2018/5/28 15:08
 */
@ApiModel(description = "新增级联展示入参")
public class MetaCascadeExtAddDTO extends AbstractDTO {




    /**
     * 所属字段id
     */
    @ApiModelProperty(notes = N_FIELDID, example = E_FIELDID)
    @NotNull
    private Integer fieldId;

    /**
     * 所属实体id
     */
    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    @NotNull
    private Integer entityId;

    /**
     * 展示字段别名
     */
    @ApiModelProperty(notes = N_ALIAS, example = E_ALIAS)
    @NotNull
    @Length(max = 64, message = "alias最大长度不能超过64")
    private String alias;

    /**
     * 是否在列表中展示
     */
    @ApiModelProperty(notes = N_LIST, example = E_LIST)
    @NotNull
    @Const(constClass = BoolConst.class)
    private Integer list;

    /**
     * 是否在详情中展示
     */
    @ApiModelProperty(notes = N_SHOW, example = E_SHOW)
    @NotNull
    @Const(constClass = BoolConst.class)
    private Integer show;

    /**
     * 是否为查询条件
     */
    @ApiModelProperty(notes = N_QUERY, example = E_QUERY)
    @NotNull
    @Const(constClass = BoolConst.class)
    private Integer query;

    /**
     * 级联实体的id
     */
    @ApiModelProperty(notes = N_CASCADEENTITYID, example = E_CASCADEENTITYID)
    @NotNull
    private Integer cascadeEntityId;

    /**
     * 级联展示字段的id
     */
    @ApiModelProperty(notes = N_CASCADEFIELDID, example = E_CASCADEFIELDID)
    @NotNull
    private Integer cascadeFieldId;



    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getQuery() {
        return query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }

    public Integer getCascadeEntityId() {
        return cascadeEntityId;
    }

    public void setCascadeEntityId(Integer cascadeEntityId) {
        this.cascadeEntityId = cascadeEntityId;
    }

    public Integer getCascadeFieldId() {
        return cascadeFieldId;
    }

    public void setCascadeFieldId(Integer cascadeFieldId) {
        this.cascadeFieldId = cascadeFieldId;
    }
}
