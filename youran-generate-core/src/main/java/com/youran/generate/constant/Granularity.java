package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【维度粒度】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum Granularity {

    /**
     * 单个值
     */
    SINGLE_VALUE(1,"单个值"),
    /**
     * 10个刻度间隔
     */
    INTERVAL_10(10,"10个刻度间隔"),
    /**
     * 100个刻度间隔
     */
    INTERVAL_100(11,"100个刻度间隔"),
    /**
     * 1000个刻度间隔
     */
    INTERVAL_1000(12,"1000个刻度间隔"),
    /**
     * 10000个刻度间隔
     */
    INTERVAL_10000(13,"10000个刻度间隔"),
    /**
     * 分钟
     */
    MINUTE(20,"分钟"),
    /**
     * 小时
     */
    HOUR(21,"小时"),
    /**
     * 天
     */
    DAY(22,"天"),
    /**
     * 周
     */
    WEEK(23,"周"),
    /**
     * 月
     */
    MONTH(24,"月"),
    /**
     * 季度
     */
    QUARTER(25,"季度"),
    /**
     * 年
     */
    YEAR(26,"年");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,10,11,12,13,20,21,22,23,24,25,26";

    private static final Map<Integer, Granularity> LOOKUP = new HashMap<>();

    static {
        for (Granularity e : Granularity.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    Granularity(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static Granularity find(Integer value) {
        return LOOKUP.get(value);
    }

    public static Granularity findByDesc(String desc){
        for (Granularity e : Granularity.values()) {
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
        Granularity theEnum = findByDesc(desc);
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
        Granularity theEnum = find(value);
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
        Granularity theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

