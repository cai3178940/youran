package com.youran.generate.constant;

import com.youran.common.exception.BusinessException;
import com.youran.common.validator.Check;

/**
 * 常量字段类型
 *
 * @author: cbb
 */
public class MetaConstType {


    /**
     * 整型
     */
    public static final int INTEGER = 1;
    /**
     * 字符串
     */
    public static final int STRING = 2;

    @Check(message = "常量类型只允许输入1或2")
    public static final boolean check(int value) {
        return INTEGER == value || STRING == value;
    }

    public static String convertString(int value) {
        if (INTEGER == value) {
            return "Integer";
        } else if (STRING == value) {
            return "String";
        }
        throw new BusinessException("不支持的常量类型");
    }

}
