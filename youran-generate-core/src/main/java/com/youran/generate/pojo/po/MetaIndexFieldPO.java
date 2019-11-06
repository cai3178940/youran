package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;

/**
 * 索引字段关联关系
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaIndexFieldPO extends AbstractPO {

    private Integer indexId;

    private Integer fieldId;

    private Integer orderNo;

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

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
