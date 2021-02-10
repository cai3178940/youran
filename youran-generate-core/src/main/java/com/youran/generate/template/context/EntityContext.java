package com.youran.generate.template.context;

import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.dto.ForeignEntityTreeNode;
import com.youran.generate.pojo.dto.MetaEntityFeatureDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.util.SwitchCaseUtil;
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
    private final MetaEntityPO metaEntity;
    /**
     * 当前实体id
     */
    private final Integer entityId;
    /**
     * 类名
     */
    private final String className;
    /**
     * 类名-首个单词转小写
     */
    private final String classNameLower;
    /**
     * 表名
     */
    private final String tableName;
    /**
     * 标题
     */
    private final String title;
    /**
     * 模块名称
     */
    private final String module;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 主键字段
     */
    private final MetaFieldPO pk;
    /**
     * 主键字段名称
     */
    private final String id;
    /**
     * 主键字段名称-首字母大写
     */
    private final String idUpper;
    /**
     * 主键字段类型
     */
    private final String type;
    /**
     * 字段列表
     */
    private final Map<Integer, MetaFieldPO> fields;
    /**
     * 是否分页查询
     */
    private final Boolean pageSign;
    /**
     * 版本字段
     */
    private final MetaFieldPO versionField;
    /**
     * 逻辑删除字段
     */
    private final MetaFieldPO delField;
    /**
     * 父id字段
     */
    private final MetaFieldPO pidField;
    /**
     * 标题字段
     */
    private final MetaFieldPO titleField;
    /**
     * 外键字段
     */
    private final Map<Integer, MetaFieldPO> fkFields;
    /**
     * 添加字段
     */
    private final Map<Integer, MetaFieldPO> insertFields;
    /**
     * 修改字段
     */
    private final Map<Integer, MetaFieldPO> updateFields;
    /**
     * 查询字段
     */
    private final Map<Integer, MetaFieldPO> queryFields;
    /**
     * 展示字段
     */
    private final Map<Integer, MetaFieldPO> showFields;
    /**
     * 列表字段
     */
    private final Map<Integer, MetaFieldPO> listFields;
    /**
     * 排序字段
     */
    private final Map<Integer, MetaFieldPO> listSortFields;
    /**
     * 创建日期字段
     */
    private final MetaFieldPO createdTimeField;
    /**
     * 创建人字段
     */
    private final MetaFieldPO createdByField;
    /**
     * 操作日期字段
     */
    private final MetaFieldPO operatedTimeField;
    /**
     * 操作人字段
     */
    private final MetaFieldPO operatedByField;

    /**
     * 持有引用的多对多关系
     */
    private final Map<MetaEntityPO, MetaManyToManyPO> holds;

    /**
     * 未持有引用的多对多关系
     */
    private final Map<MetaEntityPO, MetaManyToManyPO> unHolds;

    /**
     * 对应的外键列表(当前主键对应的其他实体外键字段)
     * 比如：
     * 1、当前实体是部门，主键是部门id
     * 2、对应实体是用户，用户中的外键字段是部门id
     * 3、则此处存放的是用户表中的部门id
     */
    private final List<MetaFieldPO> foreignFields;
    /**
     * 对应的外键实体集合(当前主键对应的其他实体)
     * 比如：
     * 1、当前实体是部门，主键是部门id
     * 2、对应实体是用户，用户中的外键字段是部门id
     * 3、则此处存放的是用户实体
     */
    private final Set<MetaEntityPO> foreignEntities;
    /**
     * 实体特性
     */
    private final MetaEntityFeatureDTO entityFeature;

    public EntityContext(MetaProjectPO project, MetaEntityPO entity) {
        super(project);
        this.metaEntity = entity;
        this.entityId = entity.getEntityId();
        this.className = entity.getClassName();
        this.classNameLower = SwitchCaseUtil.lowerFirstWord(entity.getClassName());
        this.tableName = entity.getTableName();
        this.title = entity.getTitle();
        this.module = entity.getModule();
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
        this.pidField = entity.getPidField();
        this.titleField = entity.getTitleField();
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
                sb.append(SwitchCaseUtil.lowerFirstWord(field.getForeignEntity().getClassName()))
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
     * TODO 需要重构，下沉到模板中
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
     * TODO 需要重构，下沉到模板中
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
            String classNameLowerCase = SwitchCaseUtil.lowerFirstWord(entity.getClassName());
            String className = entity.getClassName();
            StringBuilder line = new StringBuilder();
            // 增加依赖
            if(StringUtils.isNotBlank(entity.getModule())) {
                this.addImport(this.packageName + ".pojo.po."+ entity.getModule()+ "." + className + "PO");
                this.addAutowired(this.packageName + ".help." + entity.getModule(), className + "Helper");
            }else{
                this.addImport(this.packageName + ".pojo.po." + className + "PO");
                this.addAutowired(this.packageName + ".help", className + "Helper");
            }
            // 增加注入
            line.append(className).append("PO ").append(classNameLowerCase).append(" = ")
                .append(classNameLowerCase).append("Helper.save").append(className).append("Example(")
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
                throw new BusinessException("实体外键之间存在强循环依赖，请关闭外键的“不能为空”配置项");
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

    public Integer getEntityId() {
        return entityId;
    }

    public String getClassName() {
        return className;
    }

    public String getClassNameLower() {
        return classNameLower;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTitle() {
        return title;
    }

    public String getModule() {
        return module;
    }

    public String getDesc() {
        return desc;
    }

    public MetaFieldPO getPk() {
        return pk;
    }

    public String getId() {
        return id;
    }

    public String getIdUpper() {
        return idUpper;
    }

    public String getType() {
        return type;
    }

    public Boolean getPageSign() {
        return pageSign;
    }

    public MetaFieldPO getVersionField() {
        return versionField;
    }

    public MetaFieldPO getDelField() {
        return delField;
    }

    public Map<Integer, MetaFieldPO> getFields() {
        return fields;
    }

    public Map<Integer, MetaFieldPO> getFkFields() {
        return fkFields;
    }

    public Map<Integer, MetaFieldPO> getInsertFields() {
        return insertFields;
    }

    public Map<Integer, MetaFieldPO> getUpdateFields() {
        return updateFields;
    }

    public Map<Integer, MetaFieldPO> getQueryFields() {
        return queryFields;
    }

    public Map<Integer, MetaFieldPO> getShowFields() {
        return showFields;
    }

    public Map<Integer, MetaFieldPO> getListFields() {
        return listFields;
    }

    public Map<Integer, MetaFieldPO> getListSortFields() {
        return listSortFields;
    }

    public MetaFieldPO getCreatedTimeField() {
        return createdTimeField;
    }

    public MetaFieldPO getOperatedTimeField() {
        return operatedTimeField;
    }

    public MetaFieldPO getCreatedByField() {
        return createdByField;
    }

    public MetaFieldPO getOperatedByField() {
        return operatedByField;
    }

    public Map<MetaEntityPO, MetaManyToManyPO> getHolds() {
        return holds;
    }

    public Map<MetaEntityPO, MetaManyToManyPO> getUnHolds() {
        return unHolds;
    }

    public List<MetaFieldPO> getForeignFields() {
        return foreignFields;
    }

    public Set<MetaEntityPO> getForeignEntities() {
        return foreignEntities;
    }

    public MetaEntityFeatureDTO getEntityFeature() {
        return entityFeature;
    }

    public MetaFieldPO getTitleField() {
        return titleField;
    }

    public MetaFieldPO getPidField() {
        return pidField;
    }

    @Override
    public String toString() {
        return "Entity:" + className;
    }
}
