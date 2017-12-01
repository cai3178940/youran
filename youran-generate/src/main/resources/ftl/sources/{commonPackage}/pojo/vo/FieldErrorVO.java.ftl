<#include "/common.ftl">
package ${commonPackage}.pojo.vo;

import com.alibaba.fastjson.JSONObject;

<@classCom "参数错误-错误字段信息"></@classCom>
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
        return JSONObject.toJSONString(this);
    }
}
