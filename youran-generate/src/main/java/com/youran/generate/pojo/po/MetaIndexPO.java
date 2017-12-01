package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Title:元数据索引
 * Description:
 * Author: cbb
 * Create Time:2017/4/11 12:38
 */
public class MetaIndexPO extends AbstractPO implements CreateOperateDeleteVersion {

    private Integer indexId;

    private String indexName;

    private Integer entityId;

    private Integer unique;

    private List<MetaFieldPO> fields;//索引字段

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

    //添加字段
    public void addMetaField(MetaFieldPO metaFieldPO) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(metaFieldPO);
    }


    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<MetaFieldPO> getFields() {
        return fields;
    }

    public void setFields(List<MetaFieldPO> fields) {
        this.fields = fields;
    }

    public Integer getUnique() {
        return unique;
    }

    public void setUnique(Integer unique) {
        this.unique = unique;
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
