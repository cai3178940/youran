package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 字段
 *
 * @author: cbb
 * @date: 2017/4/11
 */
public class MetaFieldPO extends BasePO {

    /**
     * 字段id
     */
    private Integer fieldId;
    /**
     * 所属项目id
     */
    private Integer projectId;
    /**
     * 所属实体id
     */
    private Integer entityId;
    /**
     * java字段名称
     */
    private String jfieldName;
    /**
     * mysql字段名称
     */
    private String fieldName;
    /**
     * java字段类型
     */
    private String jfieldType;
    /**
     * mysql字段类型
     */
    private String fieldType;
    /**
     * 字段简短描述
     */
    private String fieldDesc;
    /**
     * 字段示例
     */
    private String fieldExample;
    /**
     * 字段详细描述
     */
    private String fieldComment;
    /**
     * 字段长度
     */
    private Integer fieldLength;
    /**
     * 字段精度
     */
    private Integer fieldScale;
    /**
     * 是否主键
     */
    private Boolean primaryKey;
    /**
     * 是否自动递增
     */
    private Boolean autoIncrement;
    /**
     * 是否不能为空
     */
    private Boolean notNull;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 是否外键
     */
    private Boolean foreignKey;
    /**
     * 外键实体id
     */
    private Integer foreignEntityId;
    /**
     * 外键字段id
     */
    private Integer foreignFieldId;
    /**
     * 字段编辑方式：文本框、下拉框等
     */
    private Integer editType;
    /**
     * 字典类型
     */
    private String dicType;
    /**
     * 是否新增字段
     */
    private Boolean insert;
    /**
     * 是否编辑字段
     */
    private Boolean update;
    /**
     * 是否列表字段
     */
    private Boolean list;
    /**
     * 是否支持排序
     */
    private Boolean listSort;
    /**
     * 是否详情字段
     */
    private Boolean show;
    /**
     * 是否查询字段
     */
    private Boolean query;
    /**
     * 查询方式
     */
    private Integer queryType;
    /**
     * 排序号
     */
    private Integer orderNo;
    /**
     * 特殊字段类型
     */
    private String specialField;

    /**
     * 外键对应实体
     */
    @JsonIgnore
    private MetaEntityPO foreignEntity;
    /**
     * 外键对应字段
     */
    @JsonIgnore
    private MetaFieldPO foreignField;

    /**
     * 外键对应级联扩展
     */
    @JsonIgnore
    private List<MetaCascadeExtPO> cascadeExts;
    @JsonIgnore
    private List<MetaCascadeExtPO> cascadeQueryExts;
    @JsonIgnore
    private List<MetaCascadeExtPO> cascadeShowExts;
    @JsonIgnore
    private List<MetaCascadeExtPO> cascadeListExts;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<MetaCascadeExtPO> getCascadeExts() {
        return cascadeExts;
    }

    public void setCascadeExts(List<MetaCascadeExtPO> cascadeExts) {
        this.cascadeExts = cascadeExts;
    }

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

    public List<MetaCascadeExtPO> getCascadeQueryExts() {
        return cascadeQueryExts;
    }

    public void setCascadeQueryExts(List<MetaCascadeExtPO> cascadeQueryExts) {
        this.cascadeQueryExts = cascadeQueryExts;
    }

    public List<MetaCascadeExtPO> getCascadeShowExts() {
        return cascadeShowExts;
    }

    public void setCascadeShowExts(List<MetaCascadeExtPO> cascadeShowExts) {
        this.cascadeShowExts = cascadeShowExts;
    }

    public List<MetaCascadeExtPO> getCascadeListExts() {
        return cascadeListExts;
    }

    public void setCascadeListExts(List<MetaCascadeExtPO> cascadeListExts) {
        this.cascadeListExts = cascadeListExts;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public Boolean getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Boolean getInsert() {
        return insert;
    }

    public void setInsert(Boolean insert) {
        this.insert = insert;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
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
}
