package com.youran.generate.template.context;

import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.dto.ForeignEntityTreeNode;
import com.youran.generate.pojo.dto.MetaEntityFeatureDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 实体信息上下文对象
 * <p> 将实体对象中的大部分属性拷贝到当前类中，并且额外提供了一些转换和封装
 *
 * @author cbb
 * @date 2018/8/3
 */
public class EntityContext extends BaseContext {

    /**
     * 当前实体
     */
    private MetaEntityPO metaEntity;
    /**
     * 当前实体id
     */
    private Integer entityId;
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
    private Map<Integer, MetaFieldPO> fields;
    /**
     * 是否分页查询
     */
    private Boolean pageSign;
    /**
     * 版本字段
     */
    private MetaFieldPO versionField;
    /**
     * 逻辑删除字段
     */
    private MetaFieldPO delField;
    /**
     * 外键字段
     */
    private Map<Integer, MetaFieldPO> fkFields;
    /**
     * 添加字段
     */
    private Map<Integer, MetaFieldPO> insertFields;
    /**
     * 修改字段
     */
    private Map<Integer, MetaFieldPO> updateFields;
    /**
     * 查询字段
     */
    private Map<Integer, MetaFieldPO> queryFields;
    /**
     * 展示字段
     */
    private Map<Integer, MetaFieldPO> showFields;
    /**
     * 列表字段
     */
    private Map<Integer, MetaFieldPO> listFields;
    /**
     * 排序字段
     */
    private Map<Integer, MetaFieldPO> listSortFields;
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

    /**
     * 持有引用的多对多关系
     */
    private Map<MetaEntityPO, MetaManyToManyPO> holds;

    /**
     * 未持有引用的多对多关系
     */
    private Map<MetaEntityPO, MetaManyToManyPO> unHolds;

    /**
     * 对应的外键列表(当前主键对应的其他实体外键字段)
     * 比如：
     * 1、当前实体是部门，主键是部门id
     * 2、对应实体是用户，用户中的外键字段是部门id
     * 3、则此处存放的是用户表中的部门id
     */
    private List<MetaFieldPO> foreignFields;
    /**
     * 对应的外键实体集合(当前主键对应的其他实体)
     * 比如：
     * 1、当前实体是部门，主键是部门id
     * 2、对应实体是用户，用户中的外键字段是部门id
     * 3、则此处存放的是用户实体
     */
    private Set<MetaEntityPO> foreignEntities;
    /**
     * 实体特性
     */
    private MetaEntityFeatureDTO entityFeature;

    public EntityContext(MetaProjectPO project, MetaEntityPO entity) {
        super(project);
        this.metaEntity = entity;
        this.entityId = entity.getEntityId();
        this.className = StringUtils.uncapitalize(entity.getClassName());
        this.classNameUpper = StringUtils.capitalize(entity.getClassName());
        this.tableName = entity.getTableName();
        this.title = entity.getTitle();
        this.desc = entity.getDesc();
        this.pk = entity.getPkField();
        this.id = this.pk.getJfieldName();
        this.idUpper = StringUtils.capitalize(this.id);
        this.type = this.pk.getJfieldType();
        this.fields = entity.getFields();
        this.fkFields = entity.getFkFields();
        this.pageSign = entity.getPageSign();
        this.versionField = entity.getVersionField();
        this.delField = entity.getDelField();
        this.insertFields = entity.getInsertFields();
        this.updateFields = entity.getUpdateFields();
        this.queryFields = entity.getQueryFields();
        this.showFields = entity.getShowFields();
        this.listFields = entity.getListFields();
        this.listSortFields = entity.getListSortFields();
        this.createdTimeField = entity.getCreatedTimeField();
        this.createdByField = entity.getCreatedByField();
        this.operatedTimeField = entity.getOperatedTimeField();
        this.operatedByField = entity.getOperatedByField();
        this.holds = entity.getHolds();
        this.unHolds = entity.getUnHolds();
        this.foreignFields = entity.getForeignFields();
        this.foreignEntities = entity.getForeignEntities();
        this.entityFeature = entity.getEntityFeature();
    }

    /**
     * 打印单元测试中的saveExample参数
     *
     * @return
     */
    public String printSaveExampleArg(MetaEntityPO entity) {
        StringBuilder sb = new StringBuilder();
        for (MetaFieldPO field : entity.getInsertFields().values()) {
            // 跳过非外键
            if (!field.getForeignKey()) {
                continue;
            }
            if (field.getNotNull()) {
                sb.append(StringUtils.uncapitalize(field.getForeignEntity().getClassName()))
                    .append(".get")
                    .append(StringUtils.capitalize(field.getForeignEntity().getPkField().getJfieldName()))
                    .append("(), ");
            } else {
                sb.append("null, ");
            }
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }


    /**
     * 打印单元测试中保存Example的代码
     *
     * @return
     */
    public List<String> getPrintingSaveExample() {
        // 定义保存所有树节点的map
        Map<Integer, ForeignEntityTreeNode> theMap = new HashMap<>(16);
        // 构建树
        buildForeignTreeForSave(metaEntity, null, theMap);
        // 遍历外键树节点，生成叶子优先的有序集合
        Set<ForeignEntityTreeNode> dealtSet = getOrderedForeignSet(theMap);
        // 将有序集合循环遍历并打印
        return getPrintingLinesFormDealtSet(dealtSet);
    }


    /**
     * 打印单元测试中保存多对多两个实体示例的代码
     *
     * @param otherEntity 多对多中另一个实体
     * @return
     */
    public List<String> getPrintingSaveExampleForMtm(MetaEntityPO otherEntity) {
        // 定义保存所有树1和树2的所有节点的map
        Map<Integer, ForeignEntityTreeNode> theMap = new HashMap<>(16);
        Map<Integer, ForeignEntityTreeNode> otherMap = new HashMap<>(16);
        // 构建树1和树2
        buildForeignTreeForSave(metaEntity, null, theMap);
        buildForeignTreeForSave(otherEntity, null, otherMap);

        // 合并树1和树2的map
        Map<Integer, ForeignEntityTreeNode> totalMap = new HashMap<>(theMap.size() + otherMap.size());
        totalMap.putAll(theMap);
        totalMap.putAll(otherMap);
        // 遍历外键树节点，生成叶子优先的有序集合
        Set<ForeignEntityTreeNode> dealtSet = getOrderedForeignSet(totalMap);

        // 将有序集合循环遍历并打印
        return getPrintingLinesFormDealtSet(dealtSet);
    }

    /**
     * 将有序集合循环遍历并打印
     *
     * @param dealtSet
     * @return
     */
    private List<String> getPrintingLinesFormDealtSet(Set<ForeignEntityTreeNode> dealtSet) {
        List<String> lines = new ArrayList<>();
        // 遍历有序树节点集合，进行代码打印
        for (ForeignEntityTreeNode node : dealtSet) {
            MetaEntityPO entity = node.getMetaEntity();
            String foreigncName = StringUtils.uncapitalize(entity.getClassName());
            String foreignCName = StringUtils.capitalize(entity.getClassName());
            StringBuilder line = new StringBuilder();
            // 增加依赖
            this.addImport(this.packageName + ".pojo.po." + foreignCName + "PO");
            // 增加注入
            this.addAutowired(this.packageName + ".help", foreignCName + "Helper");
            line.append(foreignCName).append("PO ").append(foreigncName).append(" = ")
                .append(foreigncName).append("Helper.save").append(foreignCName).append("Example(")
                .append(printSaveExampleArg(entity)).append(");");
            lines.add(line.toString());
        }
        return lines;
    }

    /**
     * 遍历外键树节点，生成叶子优先的有序集合
     *
     * @param totalMap
     * @return
     */
    private Set<ForeignEntityTreeNode> getOrderedForeignSet(Map<Integer, ForeignEntityTreeNode> totalMap) {
        // 定义已经按顺序处理过的树节点集合
        Set<ForeignEntityTreeNode> dealtSet = new LinkedHashSet<>();
        // 多次循环遍历totalMap，从叶子开始进行处理
        while (!totalMap.isEmpty()) {
            int startSize = totalMap.size();
            // 通过iterator来遍历，可以一边遍历一边删除数据
            for (Iterator<Map.Entry<Integer, ForeignEntityTreeNode>> it = totalMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Integer, ForeignEntityTreeNode> item = it.next();
                ForeignEntityTreeNode node = item.getValue();
                // 如果当前节点是叶子节点
                List<ForeignEntityTreeNode> children = node.getChildren();
                if (CollectionUtils.isEmpty(children) || dealtSet.containsAll(children)) {
                    // 加入处理集合中
                    dealtSet.add(node);
                    // 从总map中移除
                    it.remove();
                }
            }
            int endSize = totalMap.size();
            // 走一遍遍历没有任何效果，则报错
            if (endSize > 0 && endSize == startSize) {
                throw new BusinessException("实体外键之间存在强循环依赖");
            }
        }
        return dealtSet;
    }

    /**
     * 构建保存某个实体时，所需的整个外键强关联树【递归】
     *
     * @param metaEntity 当前处理实体
     * @param map        递归时将处理过的entity放入map，防止重复处理
     * @return
     */
    public ForeignEntityTreeNode buildForeignTreeForSave(MetaEntityPO metaEntity, ForeignEntityTreeNode parent, Map<Integer, ForeignEntityTreeNode> map) {
        // 如果已经构建过则直接返回
        if (map.containsKey(metaEntity.getEntityId())) {
            return map.get(metaEntity.getEntityId());
        }
        ForeignEntityTreeNode node = new ForeignEntityTreeNode(metaEntity, parent);
        map.put(metaEntity.getEntityId(), node);
        Map<Integer, MetaFieldPO> insertFields = metaEntity.getInsertFields();

        for (MetaFieldPO field : insertFields.values()) {
            // 插入字段是外键，并且不能为空
            if (field.getForeignKey() && field.getNotNull()) {
                node.addForeign(field);
                ForeignEntityTreeNode child = buildForeignTreeForSave(field.getForeignEntity(), node, map);
                node.addChild(child);
            }
        }
        return node;
    }


    public MetaEntityPO getMetaEntity() {
        return metaEntity;
    }

    public void setMetaEntity(MetaEntityPO metaEntity) {
        this.metaEntity = metaEntity;
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

    public Boolean getPageSign() {
        return pageSign;
    }

    public void setPageSign(Boolean pageSign) {
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

    public Map<Integer, MetaFieldPO> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(Map<Integer, MetaFieldPO> queryFields) {
        this.queryFields = queryFields;
    }

    public Map<Integer, MetaFieldPO> getShowFields() {
        return showFields;
    }

    public void setShowFields(Map<Integer, MetaFieldPO> showFields) {
        this.showFields = showFields;
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

    public MetaEntityFeatureDTO getEntityFeature() {
        return entityFeature;
    }

    public void setEntityFeature(MetaEntityFeatureDTO entityFeature) {
        this.entityFeature = entityFeature;
    }


    @Override
    public String toString() {
        return "Entity:" + className;
    }
}
