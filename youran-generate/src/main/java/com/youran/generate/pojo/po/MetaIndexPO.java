package com.youran.generate.pojo.po;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:索引</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/4/11
 */
public class MetaIndexPO extends GeneralPO {

    private Integer indexId;

    private String indexName;

    private Integer entityId;

    private Integer unique;

    private Integer uniqueCheck;

    private List<MetaFieldPO> fields;//索引字段

    //添加字段
    public void addMetaField(MetaFieldPO metaFieldPO) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(metaFieldPO);
    }


    public Integer getUniqueCheck() {
        return uniqueCheck;
    }

    public void setUniqueCheck(Integer uniqueCheck) {
        this.uniqueCheck = uniqueCheck;
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

}
