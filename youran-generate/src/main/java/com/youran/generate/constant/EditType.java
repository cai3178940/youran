package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * <p>Title: 编辑类型</p>
 * <p>Description: </p>
 * @author: cbb
 * @date: 2017/9/20
 */
public class EditType {

    /**
     * 文本框
     */
    public static final int TEXT = 1;
    /**
     * 选择框
     */
    public static final int SELECT = 2;
    /**
     * 日期框
     */
    public static final int DATE = 3;
    //...等等

    @Check
    public static final boolean check(int value) {
        return TEXT == value || SELECT == value || DATE == value;
    }
}
