package com.youran.generate.pojo.template;

import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <p>Title: 实体模型</p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/8/3
 */
public class EntityModel extends BaseModel{

    /**
     * 当前实体
     */
    private MetaEntityPO metaEntity;
    /**
     * 类名-首字母小写
     */
    private String className;
    /**
     * 类名-首字母大写
     */
    private String classNameUpper;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String desc;
    /**
     * 主键字段
     */
    private MetaFieldPO pk;
    /**
     * 主键字段名称
     */
    private String id;
    /**
     * 主键字段名称-首字母大写
     */
    private String idUpper;
    /**
     * 主键字段类型
     */
    private String type;
    /**
     * 字段列表
     */
    private List<MetaFieldPO> fields;
    /**
     * 是否分页查询
     */
    private Integer pageSign;
    /**
     * 版本字段
     */
    private MetaFieldPO versionField;
    /**
     * 逻辑删除字段
     */
    private MetaFieldPO delField;
    /**
     * 添加字段
     */
    private List<MetaFieldPO> insertFields;
    /**
     * 修改字段
     */
    private List<MetaFieldPO> updateFields;
    /**
     * 查询字段
     */
    private List<MetaFieldPO> queryFields;
    /**
     * 展示字段
     */
    private List<MetaFieldPO> showFields;
    /**
     * 列表字段
     */
    private List<MetaFieldPO> listFields;
    /**
     * 排序字段
     */
    private List<MetaFieldPO> listSortFields;
    /**
     * 创建日期字段
     */
    private MetaFieldPO createdTimeField;
    /**
     * 创建人字段
     */
    private MetaFieldPO createdByField;
    /**
     * 操作日期字段
     */
    private MetaFieldPO operatedTimeField;
    /**
     * 操作人字段
     */
    private MetaFieldPO operatedByField;


    public EntityModel(MetaProjectPO project,MetaEntityPO metaEntity){
        super(project);
        this.metaEntity = metaEntity;
        this.className = StringUtils.uncapitalize(metaEntity.getClassName());
        this.classNameUpper = StringUtils.capitalize(metaEntity.getClassName());
        this.tableName = metaEntity.getTableName();
        this.title = metaEntity.getTitle();
        this.desc = metaEntity.getDesc();
        this.pk = metaEntity.getPkField();
        this.id = this.pk.getJfieldName();
        this.idUpper = StringUtils.capitalize(this.id);
        this.type = this.pk.getJfieldType();
        this.fields = metaEntity.getFields();
        this.pageSign = metaEntity.getPageSign();
        this.versionField = metaEntity.getVersionField();
        this.delField = metaEntity.getDelField();
        this.insertFields = metaEntity.getInsertFields();
        this.updateFields = metaEntity.getUpdateFields();
        this.queryFields = metaEntity.getQueryFields();
        this.showFields = metaEntity.getShowFields();
        this.listFields = metaEntity.getListFields();
        this.listSortFields = metaEntity.getListSortFields();
        this.createdTimeField = metaEntity.getCreatedTimeField();
        this.createdByField = metaEntity.getCreatedByField();
        this.operatedTimeField = metaEntity.getOperatedTimeField();
        this.operatedByField = metaEntity.getOperatedByField();
    }


    public MetaEntityPO getMetaEntity() {
        return metaEntity;
    }

    public void setMetaEntity(MetaEntityPO metaEntity) {
        this.metaEntity = metaEntity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameUpper() {
        return classNameUpper;
    }

    public void setClassNameUpper(String classNameUpper) {
        this.classNameUpper = classNameUpper;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MetaFieldPO getPk() {
        return pk;
    }

    public void setPk(MetaFieldPO pk) {
        this.pk = pk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUpper() {
        return idUpper;
    }

    public void setIdUpper(String idUpper) {
        this.idUpper = idUpper;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MetaFieldPO> getFields() {
        return fields;
    }

    public void setFields(List<MetaFieldPO> fields) {
        this.fields = fields;
    }

    public Integer getPageSign() {
        return pageSign;
    }

    public void setPageSign(Integer pageSign) {
        this.pageSign = pageSign;
    }

    public MetaFieldPO getVersionField() {
        return versionField;
    }

    public void setVersionField(MetaFieldPO versionField) {
        this.versionField = versionField;
    }

    public MetaFieldPO getDelField() {
        return delField;
    }

    public void setDelField(MetaFieldPO delField) {
        this.delField = delField;
    }

    public List<MetaFieldPO> getInsertFields() {
        return insertFields;
    }

    public void setInsertFields(List<MetaFieldPO> insertFields) {
        this.insertFields = insertFields;
    }

    public List<MetaFieldPO> getUpdateFields() {
        return updateFields;
    }

    public void setUpdateFields(List<MetaFieldPO> updateFields) {
        this.updateFields = updateFields;
    }

    public List<MetaFieldPO> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<MetaFieldPO> queryFields) {
        this.queryFields = queryFields;
    }

    public List<MetaFieldPO> getShowFields() {
        return showFields;
    }

    public void setShowFields(List<MetaFieldPO> showFields) {
        this.showFields = showFields;
    }

    public List<MetaFieldPO> getListFields() {
        return listFields;
    }

    public void setListFields(List<MetaFieldPO> listFields) {
        this.listFields = listFields;
    }

    public List<MetaFieldPO> getListSortFields() {
        return listSortFields;
    }

    public void setListSortFields(List<MetaFieldPO> listSortFields) {
        this.listSortFields = listSortFields;
    }

    public MetaFieldPO getCreatedTimeField() {
        return createdTimeField;
    }

    public void setCreatedTimeField(MetaFieldPO createdTimeField) {
        this.createdTimeField = createdTimeField;
    }

    public MetaFieldPO getOperatedTimeField() {
        return operatedTimeField;
    }

    public void setOperatedTimeField(MetaFieldPO operatedTimeField) {
        this.operatedTimeField = operatedTimeField;
    }

    public MetaFieldPO getCreatedByField() {
        return createdByField;
    }

    public void setCreatedByField(MetaFieldPO createdByField) {
        this.createdByField = createdByField;
    }

    public MetaFieldPO getOperatedByField() {
        return operatedByField;
    }

    public void setOperatedByField(MetaFieldPO operatedByField) {
        this.operatedByField = operatedByField;
    }
}
