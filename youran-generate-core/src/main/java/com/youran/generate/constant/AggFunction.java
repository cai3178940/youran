package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【聚合函数】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum AggFunction {

    /**
     * sum
     */
    SUM(1,"sum"),
    /**
     * max
     */
    MAX(2,"max"),
    /**
     * min
     */
    MIN(3,"min"),
    /**
     * avg
     */
    AVG(4,"avg"),
    /**
     * count
     */
    COUNT(5,"count"),
    /**
     * count_distinct
     */
    COUNT_DISTINCT(6,"count_distinct");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2,3,4,5,6";

    private static final Map<Integer, AggFunction> LOOKUP = new HashMap<>();

    static {
        for (AggFunction e : AggFunction.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    AggFunction(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static AggFunction find(Integer value) {
        return LOOKUP.get(value);
    }

    public static AggFunction findByDesc(String desc){
        for (AggFunction e : AggFunction.values()) {
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
        AggFunction theEnum = findByDesc(desc);
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
        AggFunction theEnum = find(value);
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
        AggFunction theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

