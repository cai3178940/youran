package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【时间粒度】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum TimeGranularity {

    /**
     * 分钟
     */
    MINUTE(1,"分钟"),
    /**
     * 小时
     */
    HOUR(2,"小时"),
    /**
     * 天
     */
    DAY(3,"天"),
    /**
     * 周
     */
    WEEK(4,"周"),
    /**
     * 月
     */
    MONTH(5,"月"),
    /**
     * 季度
     */
    QUARTER(6,"季度"),
    /**
     * 年
     */
    YEAR(7,"年");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2,3,4,5,6,7";

    private static final Map<Integer, TimeGranularity> LOOKUP = new HashMap<>();

    static {
        for (TimeGranularity e : TimeGranularity.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    TimeGranularity(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static TimeGranularity find(Integer value) {
        return LOOKUP.get(value);
    }

    public static TimeGranularity findByDesc(String desc){
        for (TimeGranularity e : TimeGranularity.values()) {
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
        TimeGranularity theEnum = findByDesc(desc);
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
        TimeGranularity theEnum = find(value);
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
        TimeGranularity theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

