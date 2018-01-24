package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Title:元数据实体
 * Description:
 * Author: cbb
 * Create Time:2017/4/11 10:49
 */
public class MetaEntityPO extends AbstractPO implements CreateOperateDeleteVersion {


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
     */
    private Integer commonCall;
    /**
     * 是否支持分页查询
     */
    private Integer pageSign;


    /**
     * 字段列表
     */
    private List<MetaFieldPO> fields;

    /**
     * 查询字段列表
     */
    private List<MetaFieldPO> queryFields;

    /**
     * 插入字段列表
     */
    private List<MetaFieldPO> insertFields;
    /**
     * 修改字段列表
     */
    private List<MetaFieldPO> updateFields;
    /**
     * 列表字段列表
     */
    private List<MetaFieldPO> listFields;
    /**
     * 详情字段列表
     */
    private List<MetaFieldPO> showFields;
    /**
     * 索引列表
     */
    private List<MetaIndexPO> indices;
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
    private MetaFieldPO createByField;
    /**
     * 创建时间字段
     */
    private MetaFieldPO createDateField;
    /**
     * 操作人字段
     */
    private MetaFieldPO operateByField;
    /**
     * 操作时间字段
     */
    private MetaFieldPO operateDateField;
    /**
     * 乐观锁版本字段
     */
    private MetaFieldPO versionField;
    /**
     * 持有引用的多对多关系
     */
    private List<MetaManyToManyPO> holdMtms;
    /**
     * 持有的引用实体
     */
    private List<MetaEntityPO> mtmHoldRefers;
    /**
     * 未持有引用的多对多关系
     */
    private List<MetaManyToManyPO> unHoldMtms;
    /**
     * 未持的引用实体
     */
    private List<MetaEntityPO> mtmUnHoldRefers;

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

    public MetaEntityPO addField(MetaFieldPO metaFieldPO){
        if(fields==null){
            fields = new ArrayList<>();
        }
        fields.add(metaFieldPO);
        return this;
    }

    public MetaEntityPO addIndex(MetaIndexPO metaIndexPO){
        if(indices==null){
            indices = new ArrayList<>();
        }
        indices.add(metaIndexPO);
        return this;
    }

    public MetaEntityPO addHoldRefer(MetaEntityPO metaEntityPO){
        if(mtmHoldRefers==null){
            mtmHoldRefers = new ArrayList<>();
        }
        mtmHoldRefers.add(metaEntityPO);
        return this;
    }
    public MetaEntityPO addHoldMtms(MetaManyToManyPO mtm){
        if(holdMtms==null){
            holdMtms = new ArrayList<>();
        }
        holdMtms.add(mtm);
        return this;
    }

    public MetaEntityPO addUnHoldRefer(MetaEntityPO metaEntityPO){
        if(mtmUnHoldRefers==null){
            mtmUnHoldRefers = new ArrayList<>();
        }
        mtmUnHoldRefers.add(metaEntityPO);
        return this;
    }
    public MetaEntityPO addUnHoldMtms(MetaManyToManyPO mtm){
        if(unHoldMtms==null){
            unHoldMtms = new ArrayList<>();
        }
        unHoldMtms.add(mtm);
        return this;
    }

    public Integer getPageSign() {
        return pageSign;
    }

    public void setPageSign(Integer pageSign) {
        this.pageSign = pageSign;
    }

    public List<MetaManyToManyPO> getHoldMtms() {
        return holdMtms;
    }

    public void setHoldMtms(List<MetaManyToManyPO> holdMtms) {
        this.holdMtms = holdMtms;
    }

    public List<MetaManyToManyPO> getUnHoldMtms() {
        return unHoldMtms;
    }

    public void setUnHoldMtms(List<MetaManyToManyPO> unHoldMtms) {
        this.unHoldMtms = unHoldMtms;
    }

    public List<MetaEntityPO> getMtmHoldRefers() {
        return mtmHoldRefers;
    }

    public void setMtmHoldRefers(List<MetaEntityPO> mtmHoldRefers) {
        this.mtmHoldRefers = mtmHoldRefers;
    }

    public List<MetaEntityPO> getMtmUnHoldRefers() {
        return mtmUnHoldRefers;
    }

    public void setMtmUnHoldRefers(List<MetaEntityPO> mtmUnHoldRefers) {
        this.mtmUnHoldRefers = mtmUnHoldRefers;
    }

    public MetaFieldPO getVersionField() {
        return versionField;
    }

    public void setVersionField(MetaFieldPO versionField) {
        this.versionField = versionField;
    }

    public List<MetaFieldPO> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<MetaFieldPO> queryFields) {
        this.queryFields = queryFields;
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

    public List<MetaFieldPO> getListFields() {
        return listFields;
    }

    public void setListFields(List<MetaFieldPO> listFields) {
        this.listFields = listFields;
    }

    public List<MetaFieldPO> getShowFields() {
        return showFields;
    }

    public void setShowFields(List<MetaFieldPO> showFields) {
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

    public List<MetaIndexPO> getIndices() {
        return indices;
    }

    public void setIndices(List<MetaIndexPO> indices) {
        this.indices = indices;
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

    public MetaFieldPO getOperateDateField() {
        return operateDateField;
    }

    public void setOperateDateField(MetaFieldPO operateDateField) {
        this.operateDateField = operateDateField;
    }

    public MetaFieldPO getCreateByField() {
        return createByField;
    }

    public void setCreateByField(MetaFieldPO createByField) {
        this.createByField = createByField;
    }

    public MetaFieldPO getCreateDateField() {
        return createDateField;
    }

    public void setCreateDateField(MetaFieldPO createDateField) {
        this.createDateField = createDateField;
    }

    public MetaFieldPO getOperateByField() {
        return operateByField;
    }

    public void setOperateByField(MetaFieldPO operateByField) {
        this.operateByField = operateByField;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public List<MetaFieldPO> getFields() {
        return fields;
    }

    public void setFields(List<MetaFieldPO> fields) {
        this.fields = fields;
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
