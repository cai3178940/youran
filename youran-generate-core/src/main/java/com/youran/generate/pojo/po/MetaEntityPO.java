package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.util.JsonUtil;
import com.youran.generate.pojo.dto.MetaEntityFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * 实体
 *
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
     * 模块
     */
    private String module;
    /**
     * 实体描述
     */
    private String desc;
    /**
     * 是否支持分页查询
     */
    private Boolean pageSign;
    /**
     * 特性json
     * 后续有新的特性直接往里加，省的再扩展新字段
     */
    private String feature;

    /**
     * 字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> fields = new LinkedHashMap<>(16);

    /**
     * 外键字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> fkFields = new LinkedHashMap<>(16);

    /**
     * 查询字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> queryFields = new LinkedHashMap<>(16);

    /**
     * 插入字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> insertFields = new LinkedHashMap<>(16);
    /**
     * 修改字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> updateFields = new LinkedHashMap<>(16);
    /**
     * 列表字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> listFields = new LinkedHashMap<>(16);
    /**
     * 排序字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> listSortFields = new LinkedHashMap<>(16);
    /**
     * 详情字段列表
     */
    @JsonIgnore
    private Map<Integer, MetaFieldPO> showFields = new LinkedHashMap<>(16);
    /**
     * 索引列表
     */
    @JsonIgnore
    private List<MetaIndexPO> indexes = new ArrayList<>();
    /**
     * 需要校验唯一性的索引列表
     */
    @JsonIgnore
    private List<MetaIndexPO> checkUniqueIndexes;
    /**
     * 主键字段
     */
    @JsonIgnore
    private MetaFieldPO pkField;
    /**
     * 逻辑删除标识字段
     */
    @JsonIgnore
    private MetaFieldPO delField;
    /**
     * 创建人字段
     */
    @JsonIgnore
    private MetaFieldPO createdByField;
    /**
     * 创建时间字段
     */
    @JsonIgnore
    private MetaFieldPO createdTimeField;
    /**
     * 操作人字段
     */
    @JsonIgnore
    private MetaFieldPO operatedByField;
    /**
     * 操作时间字段
     */
    @JsonIgnore
    private MetaFieldPO operatedTimeField;
    /**
     * 乐观锁版本字段
     */
    @JsonIgnore
    private MetaFieldPO versionField;
    /**
     * 实体内的标题字段
     * 用于在java模板内生成findOptions服务
     */
    @JsonIgnore
    private MetaFieldPO titleField;
    /**
     * 持有引用的多对多关系
     */
    @JsonIgnore
    private Map<MetaEntityPO, MetaManyToManyPO> holds = new TreeMap<>();
    /**
     * 未持有引用的多对多关系
     */
    @JsonIgnore
    private Map<MetaEntityPO, MetaManyToManyPO> unHolds = new TreeMap<>();
    /**
     * 被对方持有引用的多对多关系
     */
    @JsonIgnore
    private Map<MetaEntityPO, MetaManyToManyPO> mtmsForOpp = new TreeMap<>();

    /**
     * 对应的外键列表(当前主键对应的其他实体外键字段)
     * 比如：
     * 1、当前实体是部门，主键是部门id
     * 2、对应实体是用户，用户中的外键字段是部门id
     * 3、则此处存放的是用户表中的部门id
     */
    @JsonIgnore
    private List<MetaFieldPO> foreignFields = new ArrayList<>();
    /**
     * 对应的外键实体集合(当前主键对应的其他实体)
     * 比如：
     * 1、当前实体是部门，主键是部门id
     * 2、对应实体是用户，用户中的外键字段是部门id
     * 3、则此处存放的是用户实体
     */
    @JsonIgnore
    private Set<MetaEntityPO> foreignEntities = new TreeSet<>();

    /**
     * 实体特性
     */
    @JsonIgnore
    private MetaEntityFeatureDTO entityFeature;

    /**
     * 初始化实体特性对象
     */
    public MetaEntityFeatureDTO initEntityFeature() {
        // 兼容旧数据，如果feature字段为空，则设置默认值
        if (StringUtils.isBlank(this.feature)) {
            this.feature = JsonUtil.toJSONString(new MetaEntityFeatureDTO());
        }
        this.entityFeature = FeatureMapper.asEntityFeatureDTO(this.feature);
        return this.entityFeature;
    }


    public MetaEntityPO addField(MetaFieldPO field) {
        fields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addFkField(MetaFieldPO field) {
        fkFields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addQueryField(MetaFieldPO field) {
        queryFields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addInsertField(MetaFieldPO field) {
        insertFields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addUpdateField(MetaFieldPO field) {
        updateFields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addListField(MetaFieldPO field) {
        listFields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addListSortField(MetaFieldPO field) {
        listSortFields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addShowField(MetaFieldPO field) {
        showFields.put(field.getFieldId(), field);
        return this;
    }

    public MetaEntityPO addForeignField(MetaFieldPO metaFieldPO) {
        foreignFields.add(metaFieldPO);
        return this;
    }


    public MetaEntityPO addForeignEntity(MetaEntityPO foreignEntity) {
        foreignEntities.add(foreignEntity);
        return this;
    }

    public MetaEntityPO addHold(MetaEntityPO entity, MetaManyToManyPO mtm) {
        holds.put(entity, mtm);
        return this;
    }

    public MetaEntityPO addUnHold(MetaEntityPO entity, MetaManyToManyPO mtm) {
        unHolds.put(entity, mtm);
        return this;
    }

    public MetaEntityPO addMtmForOpp(MetaEntityPO entity, MetaManyToManyPO mtm) {
        mtmsForOpp.put(entity, mtm);
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

    public Boolean getPageSign() {
        return pageSign;
    }

    public void setPageSign(Boolean pageSign) {
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

    public Map<MetaEntityPO, MetaManyToManyPO> getMtmsForOpp() {
        return mtmsForOpp;
    }

    public void setMtmsForOpp(Map<MetaEntityPO, MetaManyToManyPO> mtmsForOpp) {
        this.mtmsForOpp = mtmsForOpp;
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
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

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public MetaEntityFeatureDTO getEntityFeature() {
        return entityFeature;
    }

    public void setEntityFeature(MetaEntityFeatureDTO entityFeature) {
        this.entityFeature = entityFeature;
    }

    public MetaFieldPO getTitleField() {
        return titleField;
    }

    public void setTitleField(MetaFieldPO titleField) {
        this.titleField = titleField;
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
            .append(this.entityId, o.entityId)
            .toComparison();
    }
}
