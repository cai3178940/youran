package com.youran.generate.constant;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.validator.Check;

import java.util.Objects;

/**
 * 查询方式
 *
 * @author: cbb
 * @date: 2017/9/20
 */
public class QueryType {

    /**
     * 等于
     */
    public static final int EQ = 1;
    /**
     * like
     */
    public static final int LIKE = 2;
    /**
     * 大于
     */
    public static final int GT = 3;
    /**
     * 大于等于
     */
    public static final int GE = 4;
    /**
     * 小于
     */
    public static final int LT = 5;
    /**
     * 小于等于
     */
    public static final int LE = 6;
    /**
     * between
     */
    public static final int BETWEEN = 7;
    /**
     * in
     */
    public static final int IN = 8;


    /**
     * 字符串使用模糊查询的长度阈值
     */
    public static final int LIKE_LENGTH_THRESHOLD = 32;

    /**
     * 制成方法，方便模板中调用
     */
    public static final boolean isEq(Integer type) {
        return Objects.equals(EQ, type);
    }

    public static final boolean isLike(Integer type) {
        return Objects.equals(LIKE, type);
    }

    public static final boolean isGt(Integer type) {
        return Objects.equals(GT, type);
    }

    public static final boolean isGe(Integer type) {
        return Objects.equals(GE, type);
    }

    public static final boolean isLt(Integer type) {
        return Objects.equals(LT, type);
    }

    public static final boolean isLe(Integer type) {
        return Objects.equals(LE, type);
    }

    public static final boolean isBetween(Integer type) {
        return Objects.equals(BETWEEN, type);
    }

    public static final boolean isIn(Integer type) {
        return Objects.equals(IN, type);
    }

    @Check
    public static final boolean check(int value) {
        return EQ == value || LIKE == value
            || GT == value || GE == value
            || LT == value || LE == value
            || BETWEEN == value || IN == value;
    }

    /**
     * 映射符号,默认都是等号
     *
     * @param type
     * @return
     */
    public static String mapperSymbol(Integer type) {
        if (type == null) {
            return "=";
        }
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
            case IN:
                return "in";
            default:
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "queryType【" + type + "】不合法");
        }
    }


}
