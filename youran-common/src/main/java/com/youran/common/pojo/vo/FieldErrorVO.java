package com.youran.common.pojo.vo;

import com.youran.common.util.JsonUtil;

/**
 * <p>Title:参数错误-错误字段信息</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/8/24
 */
public class FieldErrorVO {

    private final String objectName;

    private final String field;

    private final String errorMsg;

    public FieldErrorVO(String dto, String field, String errorMsg) {
        this.objectName = dto;
        this.field = field;
        this.errorMsg = errorMsg;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }
}
