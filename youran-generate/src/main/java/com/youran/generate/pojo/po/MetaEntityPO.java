package com.youran.generate.pojo.po;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * <p>Title:实体</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/4/11
 */
public class MetaEntityPO extends BasePO implements Comparable<MetaEntityPO> {

    /**
     * 实体id
     */
    private Integer entityId;
    /**
     * 所属项目id
     */
    private Integer projectId;
    /**
     * 模式名
     */
    private String schemaName;
    /**
     * 类名
     */
    private String className;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 标题
     */
    private String title;
    /**
     * 实体描述
     */
    private String desc;
    /**
     * 是否支持通用服务调用
     * 【已废弃】
     */
    @Deprecated
    private Integer commonCall;
    /**
     * 是否支持分页查询
     */
    private Integer pageSign;

    /**
     * 字段列表
     */
    private Map<Integer, MetaFieldPO> fields;

    /**
     * 外键字段列表
     */
    private Map<Integer, MetaFieldPO> fkFields;

    /**
     * 查询字段列表
     */
    private Map<Integer, MetaFieldPO> queryFields;

    /**
     * 插入字段列表
     */
    private Map<Integer, MetaFieldPO> insertFields;
    /**
     * 修改字段列表
     */
    private Map<Integer, MetaFieldPO> updateFields;
    /**
     * 列表字段列表
     */
    private Map<Integer, MetaFieldPO> listFields;
    /**
     * 排序字段列表
     */
    private Map<Integer, MetaFieldPO> listSortFields;
    /**
     * 详情字段列表
     */
    private Map<Integer, MetaFieldPO> showFields;
    /**
     * 索引列表
     */
    private List<MetaIndexPO> indexes;
    /**
     * 需要校验唯一性的索引列表
     */
    private List<MetaIndexPO> checkUniqueIndexes;
    /**
     * 主键字段
     */
    private MetaFieldPO pkField;
    /**
     * 逻辑删除标识字段
     */
    private MetaFieldPO delField;
    /**
     * 创建人字段
     */
    private MetaFieldPO createdByField;
    /**
     * 创建时间字段
     */
    private MetaFieldPO createdTimeField;
    /**
     * 操作人字段
     */
    private MetaFieldPO operatedByField;
    /**
     * 操作时间字段
     */
    private MetaFieldPO operatedTimeField;
    /**
     * 乐观锁版本字段
     */
    private MetaFieldPO versionField;
    /**
     * 持有引用的多对多关系
     */
    private Map<MetaEntityPO,MetaManyToManyPO> holds;
    /**
     * 未持有引用的多对多关系
     */
    private Map<MetaEntityPO,MetaManyToManyPO> unHolds;

    /**
     * 对应的外键列表(当前主键对应的其他实体外键字段)
     * 比如：
     *  1、当前实体是部门，主键是部门id
     *  2、对应实体是用户，用户中的外键字段是部门id
     *  3、则此处存放的是用户表中的部门id
     */
    private List<MetaFieldPO> foreignFields;
    /**
     * 对应的外键实体集合(当前主键对应的其他实体)
     * 比如：
     *  1、当前实体是部门，主键是部门id
     *  2、对应实体是用户，用户中的外键字段是部门id
     *  3、则此处存放的是用户实体
     */
    private Set<MetaEntityPO> foreignEntities;


    public MetaEntityPO addField(MetaFieldPO metaFieldPO){
        if(fields==null){
            fields = new LinkedHashMap<>(16);
        }
        fields.put(metaFieldPO.getFieldId(),metaFieldPO);
        return this;
    }

    public MetaEntityPO addForeignField(MetaFieldPO metaFieldPO){
        if(foreignFields==null){
            foreignFields = new ArrayList<>();
        }
        foreignFields.add(metaFieldPO);
        return this;
    }


    public MetaEntityPO addForeignEntity(MetaEntityPO foreignEntity){
        if(foreignEntities==null){
            foreignEntities = new TreeSet<>();
        }
        foreignEntities.add(foreignEntity);
        return this;
    }





    public MetaEntityPO addIndex(MetaIndexPO metaIndexPO){
        if(indexes==null){
            indexes = new ArrayList<>();
        }
        indexes.add(metaIndexPO);
        return this;
    }

    public MetaEntityPO addHold(MetaEntityPO entity,MetaManyToManyPO mtm){
        if(holds==null){
            holds = new TreeMap<>();
        }
        holds.put(entity,mtm);
        return this;
    }

    public MetaEntityPO addUnHold(MetaEntityPO entity,MetaManyToManyPO mtm){
        if(unHolds==null){
            unHolds = new TreeMap<>();
        }
        unHolds.put(entity,mtm);
        return this;
    }



    public List<MetaFieldPO> getForeignFields() {
        return foreignFields;
    }

    public void setForeignFields(List<MetaFieldPO> foreignFields) {
        this.foreignFields = foreignFields;
    }

    public Set<MetaEntityPO> getForeignEntities() {
        return foreignEntities;
    }

    public void setForeignEntities(Set<MetaEntityPO> foreignEntities) {
        this.foreignEntities = foreignEntities;
    }

    public Integer getPageSign() {
        return pageSign;
    }

    public void setPageSign(Integer pageSign) {
        this.pageSign = pageSign;
    }

    public Map<MetaEntityPO, MetaManyToManyPO> getHolds() {
        return holds;
    }

    public void setHolds(Map<MetaEntityPO, MetaManyToManyPO> holds) {
        this.holds = holds;
    }

    public Map<MetaEntityPO, MetaManyToManyPO> getUnHolds() {
        return unHolds;
    }

    public void setUnHolds(Map<MetaEntityPO, MetaManyToManyPO> unHolds) {
        this.unHolds = unHolds;
    }

    public MetaFieldPO getVersionField() {
        return versionField;
    }

    public void setVersionField(MetaFieldPO versionField) {
        this.versionField = versionField;
    }

    public Map<Integer, MetaFieldPO> getFields() {
        return fields;
    }

    public void setFields(Map<Integer, MetaFieldPO> fields) {
        this.fields = fields;
    }

    public Map<Integer, MetaFieldPO> getFkFields() {
        return fkFields;
    }

    public void setFkFields(Map<Integer, MetaFieldPO> fkFields) {
        this.fkFields = fkFields;
    }

    public Map<Integer, MetaFieldPO> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(Map<Integer, MetaFieldPO> queryFields) {
        this.queryFields = queryFields;
    }

    public Map<Integer, MetaFieldPO> getInsertFields() {
        return insertFields;
    }

    public void setInsertFields(Map<Integer, MetaFieldPO> insertFields) {
        this.insertFields = insertFields;
    }

    public Map<Integer, MetaFieldPO> getUpdateFields() {
        return updateFields;
    }

    public void setUpdateFields(Map<Integer, MetaFieldPO> updateFields) {
        this.updateFields = updateFields;
    }

    public Map<Integer, MetaFieldPO> getListFields() {
        return listFields;
    }

    public void setListFields(Map<Integer, MetaFieldPO> listFields) {
        this.listFields = listFields;
    }

    public Map<Integer, MetaFieldPO> getListSortFields() {
        return listSortFields;
    }

    public void setListSortFields(Map<Integer, MetaFieldPO> listSortFields) {
        this.listSortFields = listSortFields;
    }

    public Map<Integer, MetaFieldPO> getShowFields() {
        return showFields;
    }

    public void setShowFields(Map<Integer, MetaFieldPO> showFields) {
        this.showFields = showFields;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MetaIndexPO> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<MetaIndexPO> indexes) {
        this.indexes = indexes;
    }

    public List<MetaIndexPO> getCheckUniqueIndexes() {
        return checkUniqueIndexes;
    }

    public void setCheckUniqueIndexes(List<MetaIndexPO> checkUniqueIndexes) {
        this.checkUniqueIndexes = checkUniqueIndexes;
    }

    public MetaFieldPO getPkField() {
        return pkField;
    }

    public void setPkField(MetaFieldPO pkField) {
        this.pkField = pkField;
    }

    public MetaFieldPO getDelField() {
        return delField;
    }

    public void setDelField(MetaFieldPO delField) {
        this.delField = delField;
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

    public MetaFieldPO getCreatedTimeField() {
        return createdTimeField;
    }

    public void setCreatedTimeField(MetaFieldPO createdTimeField) {
        this.createdTimeField = createdTimeField;
    }

    public MetaFieldPO getOperatedByField() {
        return operatedByField;
    }

    public void setOperatedByField(MetaFieldPO operatedByField) {
        this.operatedByField = operatedByField;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getCommonCall() {
        return commonCall;
    }

    public void setCommonCall(Integer commonCall) {
        this.commonCall = commonCall;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetaEntityPO that = (MetaEntityPO) o;
        return new EqualsBuilder()
            .append(entityId, that.entityId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(entityId)
            .toHashCode();
    }

    @Override
    public int compareTo(MetaEntityPO o) {
        return new CompareToBuilder()
            .append(this.entityId,o.entityId)
            .toComparison();
    }
}
