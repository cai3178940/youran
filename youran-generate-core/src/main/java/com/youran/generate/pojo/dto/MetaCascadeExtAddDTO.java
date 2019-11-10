package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.constant.PatternConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.youran.generate.pojo.example.MetaCascadeExtExample.*;

/**
 * 新增级联展示入参
 *
 * @author: cbb
 * @date: 2018/5/28
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
    @Length(max = 64, message = "alias最大长度不能超过{max}")
    @Pattern(regexp = PatternConst.J_FIELD_NAME, message = PatternConst.J_FIELD_NAME_MSG)
    private String alias;

    /**
     * 是否在列表中展示
     */
    @ApiModelProperty(notes = N_LIST, example = E_LIST)
    @NotNull
    private Boolean list;

    /**
     * 是否在详情中展示
     */
    @ApiModelProperty(notes = N_SHOW, example = E_SHOW)
    @NotNull
    private Boolean show;

    /**
     * 是否为查询条件
     */
    @ApiModelProperty(notes = N_QUERY, example = E_QUERY)
    @NotNull
    private Boolean query;

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

    public Boolean getList() {
        return list;
    }

    public void setList(Boolean list) {
        this.list = list;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean getQuery() {
        return query;
    }

    public void setQuery(Boolean query) {
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
