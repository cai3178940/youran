package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * Title: java特殊字段常量
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 15:18
 */
public class MetaSpecialField {

    public static final String DELETED = "deleted";
    public static final String CREATED_TIME = "createdTime";
    public static final String CREATED_BY = "createdBy";
    public static final String OPERATED_TIME = "operatedTime";
    public static final String OPERATED_BY = "operatedBy";
    public static final String VERSION = "version";

    @Check
    public static final boolean check(String value) {
        return DELETED.equals(value) || CREATED_TIME.equals(value)
            || CREATED_BY.equals(value) || OPERATED_TIME.equals(value)
            || OPERATED_BY.equals(value) || VERSION.equals(value);
    }
}
