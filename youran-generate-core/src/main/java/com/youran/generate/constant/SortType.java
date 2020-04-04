package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【排序方式】
 *
 * @author cbb
 * @date 2020/04/04
 */
public enum SortType {

    /**
     * 升序
     */
    ASC(1,"升序"),
    /**
     * 降序
     */
    DESC(2,"降序");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2";

    private static final Map<Integer, SortType> LOOKUP = new HashMap<>();

    static {
        for (SortType e : SortType.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    SortType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SortType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static SortType findByDesc(String desc){
        for (SortType e : SortType.values()) {
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
        SortType theEnum = findByDesc(desc);
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
        SortType theEnum = find(value);
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
        SortType theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

