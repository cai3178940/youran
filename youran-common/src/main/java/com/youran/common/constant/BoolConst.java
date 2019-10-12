package com.youran.common.constant;


import com.youran.common.validator.Check;

/**
 * <p>Title:布尔常量</p>
 * <p>Description:如果系统中使用整型0、1代表布尔值，则可以使用本常量</p>
 * @author: cbb
 * @date: 2017/6/19
 */
public class BoolConst {

    public static final int TRUE = 1;

    public static final int FALSE = 0;

    public static final boolean isFalse(Integer value){
        return value==null || FALSE==value;
    }
    public static final boolean isTrue(Integer value){
        return !isFalse(value);
    }

    @Check(message = "只允许输入0或1")
    public static final boolean check(int value) {
        return TRUE == value || FALSE == value;
    }

}
