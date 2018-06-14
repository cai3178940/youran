package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaFieldExample.*;

/**
 * Title:元数据字段列表展示对象
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 17:54
 */
public class MetaFieldListVO extends AbstractVO {

    @ApiModelProperty(notes = N_FIELDID, example = E_FIELDID)
    private Integer fieldId;

    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    private Integer entityId;

    @ApiModelProperty(notes = N_AUTOINCREMENT, example = E_AUTOINCREMENT)
    private Integer autoIncrement;

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
    private Integer insert;

    @ApiModelProperty(notes = N_JFIELDNAME, example = E_JFIELDNAME)
    private String jfieldName;

    @ApiModelProperty(notes = N_JFIELDTYPE, example = E_JFIELDTYPE)
    private String jfieldType;

    @ApiModelProperty(notes = N_LIST, example = E_LIST)
    private Integer list;

    @ApiModelProperty(notes = N_LIST_SORT, example = E_LIST_SORT)
    private Integer listSort;

    @ApiModelProperty(notes = N_NOTNULL, example = E_NOTNULL)
    private Integer notNull;

    @ApiModelProperty(notes = N_ORDERNO, example = E_ORDERNO)
    private Integer orderNo;

    @ApiModelProperty(notes = N_PRIMARYKEY, example = E_PRIMARYKEY)
    private Integer primaryKey;

    @ApiModelProperty(notes = N_FOREIGNKEY, example = E_FOREIGNKEY)
    private Integer foreignKey;

    @ApiModelProperty(notes = N_FOREIGNENTITYID, example = E_FOREIGNENTITYID)
    private Integer foreignEntityId;

    @ApiModelProperty(notes = N_FOREIGNFIELDID, example = E_FOREIGNFIELDID)
    private Integer foreignFieldId;

    @ApiModelProperty(notes = N_QUERY, example = E_QUERY)
    private Integer query;

    @ApiModelProperty(notes = N_QUERYTYPE, example = E_QUERYTYPE)
    private Integer queryType;

    @ApiModelProperty(notes = N_SHOW, example = E_SHOW)
    private Integer show;

    @ApiModelProperty(notes = N_UPDATE, example = E_UPDATE)
    private Integer update;

    @ApiModelProperty(notes = N_SPECIALFIELD, example = E_SPECIALFIELD)
    private String specialField;

    @ApiModelProperty(notes = "级联字段数量", example = "0")
    private Integer cascadeFieldNum;

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

    public Integer getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Integer getForeignFieldId() {
        return foreignFieldId;
    }

    public void setForeignFieldId(Integer foreignFieldId) {
        this.foreignFieldId = foreignFieldId;
    }

    public Integer getListSort() {
        return listSort;
    }

    public void setListSort(Integer listSort) {
        this.listSort = listSort;
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

    public Integer getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Integer autoIncrement) {
        this.autoIncrement = autoIncrement;
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

    public Integer getInsert() {
        return insert;
    }

    public void setInsert(Integer insert) {
        this.insert = insert;
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

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getNotNull() {
        return notNull;
    }

    public void setNotNull(Integer notNull) {
        this.notNull = notNull;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getQuery() {
        return query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }
}
