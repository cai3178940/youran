package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【编辑框类型】
 *
 * @author cbb
 * @date 2019/11/10
 */
public enum EditType {

    /**
     * 文本框
     */
    TEXT(1,"文本框"),
    /**
     * 单选下拉框
     */
    SELECT(2,"单选下拉框"),
    /**
     * 日期框
     */
    DATE(3,"日期框"),
    /**
     * 数字框
     */
    NUMBER(4,"数字框"),
    /**
     * 单选按钮
     */
    RADIO(5,"单选按钮"),
    /**
     * 复选框
     */
    CHECKBOX(6,"复选框"),
    /**
     * 日期时间框
     */
    DATETIME(7,"日期时间框"),
    /**
     * 多选下拉框
     */
    MULTI_SELECT(8,"多选下拉框"),
    /**
     * 多行文本框
     */
    TEXTAREA(9,"多行文本框");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2,3,4,5,6,7,8,9";

    private static final Map<Integer, EditType> LOOKUP = new HashMap<>();

    static {
        for (EditType e : EditType.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    EditType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static EditType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static EditType findByDesc(String desc){
        for (EditType e : EditType.values()) {
            if(e.getDesc().equals(desc)){
                return e;
            }
        }
        return null;
    }

    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(Integer value){
        EditType theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

