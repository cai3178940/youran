package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【过滤运算符】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum FilterOperator {

    /**
     * 等于
     */
    EQUAL(1,"等于"),
    /**
     * 小于
     */
    LT(10,"小于"),
    /**
     * 大于等于
     */
    GE(11,"大于等于"),
    /**
     * 小于等于
     */
    LE(12,"小于等于"),
    /**
     * 介于之间
     */
    BETWEEN(13,"介于之间"),
    /**
     * 不等于
     */
    NOT_EQUAL(2,"不等于"),
    /**
     * 是当前
     */
    IS_NOW(21,"是当前"),
    /**
     * 前段时间
     */
    BEFORE_TIME(22,"前段时间"),
    /**
     * 后段时间
     */
    AFTER_TIME(23,"后段时间"),
    /**
     * 包含
     */
    CONTAIN(3,"包含"),
    /**
     * 不包含
     */
    NOT_CONTAIN(4,"不包含"),
    /**
     * 为空
     */
    IS_NULL(5,"为空"),
    /**
     * 不为空
     */
    NOT_NULL(6,"不为空"),
    /**
     * like
     */
    LIKE(7,"like"),
    /**
     * 大于
     */
    GT(9,"大于");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,10,11,12,13,2,21,22,23,3,4,5,6,7,9";

    private static final Map<Integer, FilterOperator> LOOKUP = new HashMap<>();

    static {
        for (FilterOperator e : FilterOperator.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    FilterOperator(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static FilterOperator find(Integer value) {
        return LOOKUP.get(value);
    }

    public static FilterOperator findByDesc(String desc){
        for (FilterOperator e : FilterOperator.values()) {
            if(e.getDesc().equals(desc)){
                return e;
            }
        }
        return null;
    }


    /**
     * desc映射value
     *
     * @param desc
     * @return
     */
    public static Integer descToValue(String desc) {
        FilterOperator theEnum = findByDesc(desc);
        if (theEnum != null) {
            return theEnum.getValue();
        }
        return null;
    }

    /**
     * value映射desc
     *
     * @param value
     * @return
     */
    public static String valueToDesc(Integer value) {
        FilterOperator theEnum = find(value);
        if (theEnum != null) {
            return theEnum.getDesc();
        }
        return null;
    }

    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(Integer value){
        FilterOperator theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

