package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【数据项类型】
 *
 * @author cbb
 * @date 2020-04-04
 */
public enum SourceItemType {

    /**
     * 明细列
     */
    DETAIL_COLUMN(1,"明细列"),
    /**
     * 维度
     */
    DIMENSION(2,"维度"),
    /**
     * 指标
     */
    METRICS(3,"指标"),
    /**
     * where条件
     */
    WHERE(4,"where条件"),
    /**
     * having条件
     */
    HAVING(5,"having条件"),
    /**
     * 明细排序
     */
    DETAIL_ORDER(6,"明细排序"),
    /**
     * 聚合排序
     */
    AGG_ORDER(7,"聚合排序");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2,3,4,5,6,7";

    private static final Map<Integer, SourceItemType> LOOKUP = new HashMap<>();

    static {
        for (SourceItemType e : SourceItemType.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    SourceItemType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SourceItemType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static SourceItemType findByDesc(String desc){
        for (SourceItemType e : SourceItemType.values()) {
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
        SourceItemType theEnum = findByDesc(desc);
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
        SourceItemType theEnum = find(value);
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
        SourceItemType theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

