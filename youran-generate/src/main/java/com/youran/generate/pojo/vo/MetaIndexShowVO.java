package com.youran.generate.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaIndexExample.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/16 22:34
 */
public class MetaIndexShowVO {

    @ApiModelProperty(notes = N_INDEXID, example = E_INDEXID)
    private Integer indexId;

    @ApiModelProperty(notes = N_INDEXNAME, example = E_INDEXNAME)
    private String indexName;

    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    private Integer entityId;

    @ApiModelProperty(notes = N_UNIQUE, example = E_UNIQUE)
    private Integer unique;

    @ApiModelProperty(notes = N_FIELDIDS, example = E_FIELDIDS)
    private String fieldIds;

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

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getUnique() {
        return unique;
    }

    public void setUnique(Integer unique) {
        this.unique = unique;
    }

    public String getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(String fieldIds) {
        this.fieldIds = fieldIds;
    }
}
