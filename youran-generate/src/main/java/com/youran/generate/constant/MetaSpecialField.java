package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * Title: java特殊字段常量
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 15:18
 */
public class MetaSpecialField {

    public static final String DEL_SIGN = "delSign";
    public static final String CREATE_DATE = "createDate";
    public static final String CREATE_BY = "createBy";
    public static final String OPERATE_DATE = "operateDate";
    public static final String OPERATE_BY = "operateBy";
    public static final String VERSION = "version";

    @Check
    public static final boolean check(String value) {
        return DEL_SIGN.equals(value) || CREATE_DATE.equals(value)
            || CREATE_BY.equals(value) || OPERATE_DATE.equals(value)
            || OPERATE_BY.equals(value) || VERSION.equals(value);
    }
}
