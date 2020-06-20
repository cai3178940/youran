package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【自定义字段类型】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum CustomFieldType {

    /**
     * 字符串
     */
    STRING(1, "String", "字符串"),
    /**
     * 整形
     */
    INT(2, "Integer", "整形"),
    /**
     * 浮点型
     */
    DOUBLE(3, "Double", "浮点型"),
    /**
     * 日期
     */
    DATE(4, "Date", "日期"),
    /**
     * 日期时间
     */
    DATE_TIME(5, "Date", "日期时间");


    private final Integer value;
    private final String jfieldType;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2,3,4,5";

    private static final Map<Integer, CustomFieldType> LOOKUP = new HashMap<>();

    static {
        for (CustomFieldType e : CustomFieldType.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    CustomFieldType(Integer value, String jfieldType, String desc) {
        this.value = value;
        this.jfieldType = jfieldType;
        this.desc = desc;
    }

    public static CustomFieldType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static CustomFieldType findByDesc(String desc) {
        for (CustomFieldType e : CustomFieldType.values()) {
            if (e.getDesc().equals(desc)) {
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
        CustomFieldType theEnum = findByDesc(desc);
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
        CustomFieldType theEnum = find(value);
        if (theEnum != null) {
            return theEnum.getDesc();
        }
        return null;
    }

    /**
     * value映射jfieldType
     *
     * @param value
     * @return
     */
    public static String valueToJfieldType(Integer value) {
        CustomFieldType theEnum = find(value);
        if (theEnum != null) {
            return theEnum.getJfieldType();
        }
        return null;
    }

    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(Integer value) {
        CustomFieldType theEnum = find(value);
        return theEnum != null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getJfieldType() {
        return jfieldType;
    }
}

