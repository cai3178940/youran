package com.youran.common.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:错误代码枚举</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/10/13
 */
public enum ErrorCode {

    /**
     * 参数校验失败
     */
    ERR_VALIDATION("400", "参数校验失败"),
    /**
     * 并发访问失败
     */
    CONCURRENCY_FAILURE("409", "并发访问失败"),
    /**
     * http method不允许
     */
    METHOD_NOT_SUPPORTED("405", "http method不允许"),
    /**
     * 系统内部错误
     */
    INTERNAL_SERVER_ERROR("500", "系统内部错误");


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
