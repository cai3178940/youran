package com.youran.common.constant;


import com.youran.common.validator.Check;

/**
 * Title:布尔常量
 * Description:
 * Author: cbb
 * Create Time:2017/6/19 09:31
 */
public class BoolConst {

    //数据库bool字段值
    public static final int TRUE = 1;

    public static final int FALSE = 0;

    @Check(message = "只允许输入0或1")
    public static final boolean check(int value) {
        return TRUE == value || FALSE == value;
    }
}
