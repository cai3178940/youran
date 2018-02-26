package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.Date;

/**
 * Title:元数据字段
 * Description:
 * Author: cbb
 * Create Time:2017/4/11 10:49
 */
public class MetaFieldPO extends AbstractPO implements CreateOperateDeleteVersion {

    private Integer fieldId;
    private Integer entityId;
    private String jfieldName;
    private String fieldName;
    private String jfieldType;
    private String fieldType;
    private String fieldDesc;
    private String fieldExample;
    private String fieldComment;
    private Integer fieldLength;
    private Integer fieldScale;
    private Integer primaryKey;
    private Integer autoIncrement;
    private Integer notNull;
    private String defaultValue;
    private Integer foreignKey;
    private Integer foreignEntityId;
    private Integer foreignFieldId;
    private Integer editType;
    private String dicType;
    private Integer insert;
    private Integer update;
    private Integer list;
    private Integer listSort;
    private Integer show;
    private Integer query;
    private Integer queryType;
    private Integer orderNo;
    private String specialField;

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

    /**
     * 外键对应实体
     */
    private MetaEntityPO foreignEntity;
    /**
     * 外键对应字段
     */
    private MetaFieldPO foreignField;

    public MetaEntityPO getForeignEntity() {
        return foreignEntity;
    }

    public void setForeignEntity(MetaEntityPO foreignEntity) {
        this.foreignEntity = foreignEntity;
    }

    public MetaFieldPO getForeignField() {
        return foreignField;
    }

    public void setForeignField(MetaFieldPO foreignField) {
        this.foreignField = foreignField;
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

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
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

    public String getJfieldName() {
        return jfieldName;
    }

    public void setJfieldName(String jfieldName) {
        this.jfieldName = jfieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJfieldType() {
        return jfieldType;
    }

    public void setJfieldType(String jfieldType) {
        this.jfieldType = jfieldType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
    }

    public Integer getFieldScale() {
        return fieldScale;
    }

    public void setFieldScale(Integer fieldScale) {
        this.fieldScale = fieldScale;
    }

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Integer autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Integer getNotNull() {
        return notNull;
    }

    public void setNotNull(Integer notNull) {
        this.notNull = notNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getEditType() {
        return editType;
    }

    public void setEditType(Integer editType) {
        this.editType = editType;
    }

    public String getDicType() {
        return dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public Integer getInsert() {
        return insert;
    }

    public void setInsert(Integer insert) {
        this.insert = insert;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getListSort() {
        return listSort;
    }

    public void setListSort(Integer listSort) {
        this.listSort = listSort;
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

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperateBy() {
        return operateBy;
    }

    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDelSign() {
        return delSign;
    }

    public void setDelSign(Integer delSign) {
        this.delSign = delSign;
    }
}
