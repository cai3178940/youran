package com.youran.common.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * Title: 错误代码枚举
 * Description:
 * Author: cbb
 * Create Time:2017/10/13 16:07
 */
public enum ErrorCode {

    /**
     * 参数校验失败
     */
    ERR_VALIDATION(400, "参数校验失败"),
    /**
     * 并发访问失败
     */
    CONCURRENCY_FAILURE(409, "并发访问失败"),
    /**
     * http method不允许
     */
    METHOD_NOT_SUPPORTED(405, "http method不允许"),
    /**
     * 系统内部错误
     */
    INTERNAL_SERVER_ERROR(500, "系统内部错误");


    private final Integer value;
    private final String desc;

    private static final Map<Integer, ErrorCode> LOOKUP = new HashMap<>();

    static {
        for (ErrorCode e : ErrorCode.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    ErrorCode(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ErrorCode find(Integer value) {
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

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }



}
