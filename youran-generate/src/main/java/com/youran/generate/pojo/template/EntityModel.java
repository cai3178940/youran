package com.youran.generate.pojo.template;

import com.youran.common.constant.BoolConst;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

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


    /**
     * 打印单元测试中的saveExample参数
     * @return
     */
    public String printSaveExampleArg(MetaEntityPO entity){
        StringBuilder sb = new StringBuilder();
        for (MetaFieldPO field : entity.getInsertFields()) {
            // 跳过非外键
            if(field.getForeignKey()==BoolConst.FALSE){
                continue;
            }
            if(field.getNotNull()==BoolConst.TRUE){
                sb.append(StringUtils.uncapitalize(field.getForeignEntity().getClassName()))
                    .append(".get")
                    .append(StringUtils.capitalize(field.getForeignEntity().getPkField().getJfieldName()))
                    .append("(), ");
            }else{
                sb.append("null, ");
            }
        }
        if(sb.length()>0){
            sb.delete(sb.length()-2,sb.length());
        }
        return sb.toString();
    }



    /**
     * 打印单元测试中保存Example的代码
     * @return
     */
    public List<String> getPrintingSaveExample(){
        // 定义保存所有树节点的map
        Map<Integer, ForeignEntityTreeNode> theMap = new HashMap<>();
        // 构建树
        buildForeignTreeForSave(metaEntity,null, theMap);
        // 遍历外键树节点，生成叶子优先的有序集合
        Set<ForeignEntityTreeNode> dealtSet = getOrderedForeignSet(theMap);
        // 将有序集合循环遍历并打印
        return getPrintingLinesFormDealtSet(dealtSet);
    }





    /**
     * 打印单元测试中保存多对多两个实体示例的代码
     * @param otherEntity 多对多中另一个实体
     * @return
     */
    public List<String> getPrintingSaveExampleForMtm(MetaEntityPO otherEntity){
        // 定义保存所有树1和树2的所有节点的map
        Map<Integer, ForeignEntityTreeNode> theMap = new HashMap<>();
        Map<Integer, ForeignEntityTreeNode> otherMap = new HashMap<>();
        // 构建树1和树2
        buildForeignTreeForSave(metaEntity,null, theMap);
        buildForeignTreeForSave(otherEntity,null, otherMap);

        // 合并树1和树2的map
        Map<Integer, ForeignEntityTreeNode> totalMap = new HashMap<>(theMap.size()+otherMap.size());
        totalMap.putAll(theMap);
        totalMap.putAll(otherMap);
        // 遍历外键树节点，生成叶子优先的有序集合
        Set<ForeignEntityTreeNode> dealtSet = getOrderedForeignSet(totalMap);

        // 将有序集合循环遍历并打印
        return getPrintingLinesFormDealtSet(dealtSet);
    }

    /**
     * 将有序集合循环遍历并打印
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
            this.addImport(this.packageName+".pojo.po."+foreignCName+"PO");
            // 增加注入
            this.addAutowired(this.packageName+".help",foreignCName+"Helper");
            line.append(foreignCName).append("PO ").append(foreigncName).append(" = ")
                .append(foreigncName).append("Helper.save").append(foreignCName).append("Example(")
                .append(printSaveExampleArg(entity)).append(");");
            lines.add(line.toString());
        }
        return lines;
    }

    /**
     * 遍历外键树节点，生成叶子优先的有序集合
     * @param totalMap
     * @return
     */
    private Set<ForeignEntityTreeNode> getOrderedForeignSet(Map<Integer, ForeignEntityTreeNode> totalMap) {
        // 定义已经按顺序处理过的树节点集合
        Set<ForeignEntityTreeNode> dealtSet = new LinkedHashSet<>();
        // 多次循环遍历totalMap，从叶子开始进行处理
        while(!totalMap.isEmpty()){
            int startSize = totalMap.size();
            // 通过iterator来遍历，可以一边遍历一边删除数据
            for (Iterator<Map.Entry<Integer, ForeignEntityTreeNode>> it = totalMap.entrySet().iterator(); it.hasNext();){
                Map.Entry<Integer,ForeignEntityTreeNode> item = it.next();
                ForeignEntityTreeNode node = item.getValue();
                // 如果当前节点是叶子节点
                List<ForeignEntityTreeNode> children = node.getChildren();
                if(CollectionUtils.isEmpty(children) || dealtSet.containsAll(children)){
                    // 加入处理集合中
                    dealtSet.add(node);
                    // 从总map中移除
                    it.remove();
                }
            }
            int endSize = totalMap.size();
            // 走一遍遍历没有任何效果，则报错
            if(endSize>0 && endSize==startSize){
                throw new RuntimeException("实体外键之间存在强循环依赖");
            }
        }
        return dealtSet;
    }

    /**
     * 构建保存某个实体时，所需的整个外键强关联树【递归】
     * @param metaEntity 当前处理实体
     * @param map 递归时将处理过的entity放入map，防止重复处理
     * @return
     */
    public ForeignEntityTreeNode buildForeignTreeForSave(MetaEntityPO metaEntity, ForeignEntityTreeNode parent, Map<Integer,ForeignEntityTreeNode> map){
        // 如果已经构建过则直接返回
        if(map.containsKey(metaEntity.getEntityId())){
            return map.get(metaEntity.getEntityId());
        }
        ForeignEntityTreeNode node = new ForeignEntityTreeNode(metaEntity,parent);
        map.put(metaEntity.getEntityId(),node);
        List<MetaFieldPO> insertFields = metaEntity.getInsertFields();

        for (MetaFieldPO field : insertFields) {
            // 插入字段是外键，并且不能为空
            if(field.getForeignKey()==BoolConst.TRUE && field.getNotNull()==BoolConst.TRUE){
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
