<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "java.util.HashMap"/>
<@import "java.util.Map"/>
<@classCom "错误代码枚举"/>
public enum ErrorCode {

    /**
     * 参数校验失败
     */
    ERR_VALIDATION("400", "error.err_validation"),
    /**
     * 并发访问失败
     */
    CONCURRENCY_FAILURE("409", "error.concurrency_failure"),
    /**
     * http method不允许
     */
    METHOD_NOT_SUPPORTED("405", "error.method_not_supported"),
    /**
     * 系统内部错误
     */
    INTERNAL_SERVER_ERROR("500", "error.internal_server_error"),
    /**
     * 键值重复
     */
    DUPLICATE_KEY("521", "error.duplicate_key"),
    /**
     * 记录未找到
     */
    RECORD_NOT_FIND("522", "error.record_not_find"),
    /**
     * 参数为空
     */
    PARAM_IS_NULL("523", "error.param_is_null"),
    /**
     * 级联删除异常
     */
    CASCADE_DELETE_ERROR("524", "error.cascade_delete_error");


    private final String value;
    private final String desc;

    private static final Map<String, ErrorCode> LOOKUP = new HashMap<>();

    static {
        for (ErrorCode e : ErrorCode.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    ErrorCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ErrorCode find(String value) {
        return LOOKUP.get(value);
    }

    public static ErrorCode findByDesc(String desc){
        for (ErrorCode e : ErrorCode.values()) {
            if(e.getDesc().equals(desc)){
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.constant;

<@printImport/>

${code}
