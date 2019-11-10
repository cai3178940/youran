package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 索引
 *
 * @author: cbb
 * @date: 2017/4/11
 */
public class MetaIndexPO extends BasePO {

    /**
     * 索引id
     */
    private Integer indexId;
    /**
     * 所属项目id
     */
    private Integer projectId;
    /**
     * 索引名称
     */
    private String indexName;
    /**
     * 实体id
     */
    private Integer entityId;
    /**
     * 是否唯一索引
     */
    private Boolean unique;
    /**
     * 是否唯一校验
     */
    private Boolean uniqueCheck;
    /**
     * 索引中的字段id列表
     */
    private List<Integer> fieldIds;
    /**
     * 索引字段列表
     */
    @JsonIgnore
    private List<MetaFieldPO> fields;


    public void resetFieldIds() {
        if (CollectionUtils.isEmpty(fields)) {
            this.fieldIds = Collections.emptyList();
        } else {
            this.fieldIds = fields.stream()
                .map(MetaFieldPO::getFieldId)
                .collect(Collectors.toList());
        }
    }

    /**
     * 添加字段
     *
     * @param metaFieldPO
     */
    public void addMetaField(MetaFieldPO metaFieldPO) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(metaFieldPO);
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public List<Integer> getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(List<Integer> fieldIds) {
        this.fieldIds = fieldIds;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Boolean getUniqueCheck() {
        return uniqueCheck;
    }

    public void setUniqueCheck(Boolean uniqueCheck) {
        this.uniqueCheck = uniqueCheck;
    }
}
