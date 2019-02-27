package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * <p>Title: java特殊字段常量</p>
 * <p>Description: </p>
 * @author: cbb
 * @date: 2017/9/20
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
