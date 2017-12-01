package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * Title: 编辑类型
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 15:20
 */
public class EditType {
    public static final int TEXT = 1;//文本框
    public static final int SELECT = 2;//选择框
    public static final int DATE = 3;//日期框
    //...等等

    @Check
    public static final boolean check(int value) {
        return TEXT == value || SELECT == value || DATE == value;
    }
}
