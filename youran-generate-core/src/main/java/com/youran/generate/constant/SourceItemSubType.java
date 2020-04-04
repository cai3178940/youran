package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【数据项子类型】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum SourceItemSubType {

    /**
     * 无
     */
    NONE(0,"无"),
    /**
     * 时间维度
     */
    DIMENSION_TIME(21,"时间维度"),
    /**
     * 字段维度
     */
    DIMENSION_FIELD(22,"字段维度"),
    /**
     * 数值指标
     */
    METRICS_NUM(31,"数值指标"),
    /**
     * 百分比指标
     */
    METRICS_PERCENT(32,"百分比指标");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "0,21,22,31,32";

    private static final Map<Integer, SourceItemSubType> LOOKUP = new HashMap<>();

    static {
        for (SourceItemSubType e : SourceItemSubType.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    SourceItemSubType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SourceItemSubType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static SourceItemSubType findByDesc(String desc){
        for (SourceItemSubType e : SourceItemSubType.values()) {
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
        SourceItemSubType theEnum = findByDesc(desc);
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
        SourceItemSubType theEnum = find(value);
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
        SourceItemSubType theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

