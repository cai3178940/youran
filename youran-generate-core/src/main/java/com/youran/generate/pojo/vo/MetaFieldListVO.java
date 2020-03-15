package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.youran.generate.pojo.example.MetaFieldExample.*;

/**
 * 字段列表展示对象
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaFieldListVO extends AbstractVO {

    @ApiModelProperty(notes = N_FIELDID, example = E_FIELDID)
    private Integer fieldId;

    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    private Integer entityId;

    @ApiModelProperty(notes = N_PK_STRATEGY, example = E_PK_STRATEGY)
    private Integer pkStrategy;

    @ApiModelProperty(notes = N_DEFAULTVALUE, example = E_DEFAULTVALUE)
    private String defaultValue;

    @ApiModelProperty(notes = N_DICTYPE, example = E_DICTYPE)
    private String dicType;

    @ApiModelProperty(notes = N_EDITTYPE, example = E_EDITTYPE)
    private Integer editType;

    @ApiModelProperty(notes = N_FIELDCOMMENT, example = E_FIELDCOMMENT)
    private String fieldComment;

    @ApiModelProperty(notes = N_FIELDDESC, example = E_FIELDDESC)
    private String fieldDesc;

    @ApiModelProperty(notes = N_FIELDEXAMPLE, example = E_FIELDEXAMPLE)
    private String fieldExample;

    @ApiModelProperty(notes = N_FIELDLENGTH, example = E_FIELDLENGTH)
    private Integer fieldLength;

    @ApiModelProperty(notes = N_FIELDNAME, example = E_FIELDNAME)
    private String fieldName;

    @ApiModelProperty(notes = N_FIELDSCALE, example = E_FIELDSCALE)
    private Integer fieldScale;

    @ApiModelProperty(notes = N_FIELDTYPE, example = E_FIELDTYPE)
    private String fieldType;

    @ApiModelProperty(notes = N_INSERT, example = E_INSERT)
    private Boolean insert;

    @ApiModelProperty(notes = N_JFIELDNAME, example = E_JFIELDNAME)
    private String jfieldName;

    @ApiModelProperty(notes = N_JFIELDTYPE, example = E_JFIELDTYPE)
    private String jfieldType;

    @ApiModelProperty(notes = N_LIST, example = E_LIST)
    private Boolean list;

    @ApiModelProperty(notes = N_COLUMN_WIDTH, example = E_COLUMN_WIDTH)
    private Integer columnWidth;

    @ApiModelProperty(notes = N_LIST_SORT, example = E_LIST_SORT)
    private Boolean listSort;

    @ApiModelProperty(notes = N_NOTNULL, example = E_NOTNULL)
    private Boolean notNull;

    @ApiModelProperty(notes = N_ORDERNO, example = E_ORDERNO)
    private Integer orderNo;

    @ApiModelProperty(notes = N_PRIMARYKEY, example = E_PRIMARYKEY)
    private Boolean primaryKey;

    @ApiModelProperty(notes = N_FOREIGNKEY, example = E_FOREIGNKEY)
    private Boolean foreignKey;

    @ApiModelProperty(notes = N_FOREIGNENTITYID, example = E_FOREIGNENTITYID)
    private Integer foreignEntityId;

    @ApiModelProperty(notes = N_FOREIGNFIELDID, example = E_FOREIGNFIELDID)
    private Integer foreignFieldId;

    @ApiModelProperty(notes = N_QUERY, example = E_QUERY)
    private Boolean query;

    @ApiModelProperty(notes = N_QUERYTYPE, example = E_QUERYTYPE)
    private Integer queryType;

    @ApiModelProperty(notes = N_SHOW, example = E_SHOW)
    private Boolean show;

    @ApiModelProperty(notes = N_UPDATE, example = E_UPDATE)
    private Boolean update;

    @ApiModelProperty(notes = N_SPECIALFIELD, example = E_SPECIALFIELD)
    private String specialField;

    @ApiModelProperty(notes = "级联字段数量", example = "0")
    private Integer cascadeFieldNum;

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Integer getCascadeFieldNum() {
        return cascadeFieldNum;
    }

    public void setCascadeFieldNum(Integer cascadeFieldNum) {
        this.cascadeFieldNum = cascadeFieldNum;
    }

    public Integer getForeignEntityId() {
        return foreignEntityId;
    }

    public void setForeignEntityId(Integer foreignEntityId) {
        this.foreignEntityId = foreignEntityId;
    }

    public Integer getForeignFieldId() {
        return foreignFieldId;
    }

    public void setForeignFieldId(Integer foreignFieldId) {
        this.foreignFieldId = foreignFieldId;
    }

    public String getFieldExample() {
        return fieldExample;
    }

    public void setFieldExample(String fieldExample) {
        this.fieldExample = fieldExample;
    }

    public String getSpecialField() {
        return specialField;
    }

    public void setSpecialField(String specialField) {
        this.specialField = specialField;
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDicType() {
        return dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public Integer getEditType() {
        return editType;
    }

    public void setEditType(Integer editType) {
        this.editType = editType;
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldScale() {
        return fieldScale;
    }

    public void setFieldScale(Integer fieldScale) {
        this.fieldScale = fieldScale;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getJfieldName() {
        return jfieldName;
    }

    public void setJfieldName(String jfieldName) {
        this.jfieldName = jfieldName;
    }

    public String getJfieldType() {
        return jfieldType;
    }

    public void setJfieldType(String jfieldType) {
        this.jfieldType = jfieldType;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Integer getPkStrategy() {
        return pkStrategy;
    }

    public void setPkStrategy(Integer pkStrategy) {
        this.pkStrategy = pkStrategy;
    }

    public Boolean getInsert() {
        return insert;
    }

    public void setInsert(Boolean insert) {
        this.insert = insert;
    }

    public Boolean getList() {
        return list;
    }

    public void setList(Boolean list) {
        this.list = list;
    }

    public Boolean getListSort() {
        return listSort;
    }

    public void setListSort(Boolean listSort) {
        this.listSort = listSort;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Boolean getQuery() {
        return query;
    }

    public void setQuery(Boolean query) {
        this.query = query;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("fieldId", fieldId)
            .append("entityId", entityId)
            .append("pkStrategy", pkStrategy)
            .append("defaultValue", defaultValue)
            .append("dicType", dicType)
            .append("editType", editType)
            .append("fieldComment", fieldComment)
            .append("fieldDesc", fieldDesc)
            .append("fieldExample", fieldExample)
            .append("fieldLength", fieldLength)
            .append("fieldName", fieldName)
            .append("fieldScale", fieldScale)
            .append("fieldType", fieldType)
            .append("insert", insert)
            .append("jfieldName", jfieldName)
            .append("jfieldType", jfieldType)
            .append("list", list)
            .append("listSort", listSort)
            .append("notNull", notNull)
            .append("orderNo", orderNo)
            .append("primaryKey", primaryKey)
            .append("foreignKey", foreignKey)
            .append("foreignEntityId", foreignEntityId)
            .append("foreignFieldId", foreignFieldId)
            .append("query", query)
            .append("queryType", queryType)
            .append("show", show)
            .append("update", update)
            .append("specialField", specialField)
            .append("cascadeFieldNum", cascadeFieldNum)
            .toString();
    }
}
