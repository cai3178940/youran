package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * Title:查询方式
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 15:19
 */
public class QueryType {

    public static final int EQ = 1;//等于
    public static final int LIKE = 2;//like
    public static final int GT = 3;//大于
    public static final int GE = 4;//大于等于
    public static final int LT = 5;//小于
    public static final int LE = 6;//小于等于
    public static final int BETWEEN = 7;//between

    @Check
    public static final boolean check(int value) {
        return EQ == value || LIKE == value
            || GT == value || GE == value
            || LT == value || LE == value
            || BETWEEN == value;
    }

    public static String mapperQueryType(int type) {
        switch (type) {
            case EQ:
                return "=";
            case LIKE:
                return "like";
            case GT:
                return ">";
            case GE:
                return ">=";
            case LT:
                return "&lt;";
            case LE:
                return "&lt;=";
            case BETWEEN:
                return "between";
            default:
                return null;
        }
    }

}
