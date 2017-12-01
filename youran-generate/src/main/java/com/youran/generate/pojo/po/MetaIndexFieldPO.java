package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;

/**
 * Title:索引字段关联关系
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 09:48
 */
public class MetaIndexFieldPO extends AbstractPO {

    private Integer indexId;

    private Integer fieldId;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }
}
