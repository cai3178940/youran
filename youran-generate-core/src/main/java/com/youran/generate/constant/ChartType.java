package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【图表类型】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum ChartType {

    /**
     * 明细表
     */
    DETAIL_LIST(1,"明细表"),
    /**
     * 聚合表
     */
    AGG_TABLE(2,"聚合表"),
    /**
     * 柱线图
     */
    BAR_LINE(3,"柱线图"),
    /**
     * 饼图
     */
    PIE(4,"饼图");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2,3,4";

    private static final Map<Integer, ChartType> LOOKUP = new HashMap<>();

    static {
        for (ChartType e : ChartType.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    ChartType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ChartType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static ChartType findByDesc(String desc){
        for (ChartType e : ChartType.values()) {
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
        ChartType theEnum = findByDesc(desc);
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
        ChartType theEnum = find(value);
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
        ChartType theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

