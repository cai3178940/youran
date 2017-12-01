package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * Title:常量字段类型
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 15:17
 */
public class MetaConstType {

    public static final int INTEGER = 1;//整型
    public static final int STRING = 2;//字符串

    @Check(message = "常量类型只允许输入1或2")
    public static final boolean check(int value) {
        return INTEGER == value || STRING == value;
    }
}
