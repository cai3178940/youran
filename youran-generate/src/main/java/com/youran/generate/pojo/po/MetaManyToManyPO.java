package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.Date;

/**
 * Title: 多对多关联关系
 * Description:
 * Author: cbb
 * Create Time:2017/7/4 16:25
 */
public class MetaManyToManyPO extends AbstractPO implements CreateOperateDeleteVersion {

    private Integer mtmId;
    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 关联表名
     */
    private String tableName;

    /**
     * 模式名
     */
    private String schemaName;

    /**
     * 关联描述
     */
    private String desc;
    /**
     * 实体A的id
     */
    private Integer entityId1;

    /**
     * 实体B的id
     */
    private Integer entityId2;

    /**
     * 实体A是否持有B引用
     */
    private Integer holdRefer1;
    /**
     * 实体B是否持有A引用
     */
    private Integer holdRefer2;

    /**
     * 引用实体A
     */
    private MetaEntityPO refer1;
    /**
     * 引用实体B
     */
    private MetaEntityPO refer2;

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public MetaEntityPO getRefer2() {
        return refer2;
    }

    public void setRefer2(MetaEntityPO refer2) {
        this.refer2 = refer2;
    }

    public MetaEntityPO getRefer1() {
        return refer1;
    }

    public void setRefer1(MetaEntityPO refer1) {
        this.refer1 = refer1;
    }

    public Integer getMtmId() {
        return mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEntityId1() {
        return entityId1;
    }

    public void setEntityId1(Integer entityId1) {
        this.entityId1 = entityId1;
    }

    public Integer getEntityId2() {
        return entityId2;
    }

    public void setEntityId2(Integer entityId2) {
        this.entityId2 = entityId2;
    }

    public Integer getHoldRefer1() {
        return holdRefer1;
    }

    public void setHoldRefer1(Integer holdRefer1) {
        this.holdRefer1 = holdRefer1;
    }

    public Integer getHoldRefer2() {
        return holdRefer2;
    }

    public void setHoldRefer2(Integer holdRefer2) {
        this.holdRefer2 = holdRefer2;
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
