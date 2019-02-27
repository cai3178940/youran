package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

import static com.youran.generate.pojo.example.MetaCascadeExtExample.*;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2018/5/28
 */
public class MetaCascadeExtListVO extends AbstractVO {


    /**
     * 主键id
     */
    @ApiModelProperty(notes = N_CASCADEEXTID, example = E_CASCADEEXTID)
    private Integer cascadeExtId;

    /**
     * 所属字段id
     */
    @ApiModelProperty(notes = N_FIELDID, example = E_FIELDID)
    private Integer fieldId;

    /**
     * 所属实体id
     */
    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    private Integer entityId;

    /**
     * 展示字段别名
     */
    @ApiModelProperty(notes = N_ALIAS, example = E_ALIAS)
    private String alias;

    /**
     * 是否在列表中展示
     */
    @ApiModelProperty(notes = N_LIST, example = E_LIST)
    private Integer list;

    /**
     * 是否在详情中展示
     */
    @ApiModelProperty(notes = N_SHOW, example = E_SHOW)
    private Integer show;

    /**
     * 是否为查询条件
     */
    @ApiModelProperty(notes = N_QUERY, example = E_QUERY)
    private Integer query;

    /**
     * 级联实体的id
     */
    @ApiModelProperty(notes = N_CASCADEENTITYID, example = E_CASCADEENTITYID)
    private Integer cascadeEntityId;

    /**
     * 级联展示字段的id
     */
    @ApiModelProperty(notes = N_CASCADEFIELDID, example = E_CASCADEFIELDID)
    private Integer cascadeFieldId;

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



    @ApiModelProperty(notes = N_CREATEDTIME, example = E_CREATEDTIME)
    private Date createdTime;


    public Integer getCascadeExtId() {
        return cascadeExtId;
    }

    public void setCascadeExtId(Integer cascadeExtId) {
        this.cascadeExtId = cascadeExtId;
    }

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("cascadeExtId", cascadeExtId)
            .append("fieldId", fieldId)
            .append("entityId", entityId)
            .append("alias", alias)
            .append("list", list)
            .append("show", show)
            .append("query", query)
            .append("cascadeEntityId", cascadeEntityId)
            .append("cascadeFieldId", cascadeFieldId)
            .append("cascadeJfieldName", cascadeJfieldName)
            .append("cascadeFieldDesc", cascadeFieldDesc)
            .append("createdTime", createdTime)
            .toString();
    }
}
