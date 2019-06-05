package com.youran.generate.constant;


/**
 * <p>Title: 系统常量</p>
 * <p>Description: </p>
 * @author: cbb
 * @date: 2017/5/12
 */
public class GenerateConst {

    public static final String API_PATH = "${youran.apiPath:/}";
    public static final String WS_API_PATH = "${youran.wsApiPath:/}";

    /**
     * 字段空值常量
     */
    public static final String METAFIELD_NULL_VALUE = "NULL";

    public static final String DEFAULT_ERROR_MSG = "系统bug,请联系管理员";

}
