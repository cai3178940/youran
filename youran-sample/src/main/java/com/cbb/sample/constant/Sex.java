package com.cbb.sample.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 14:56
 */
public enum  Sex {

    MAN(1,"男"),
    WOMAN(2,"女");


    private final Integer value;
    private final String desc;

    private static final Map<Integer, Sex> lookup = new HashMap<>();

    static {
        for (Sex e : Sex.values()) {
            lookup.put(e.value, e);
        }
    }


    Sex(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static Sex find(Integer value) {
        return lookup.get(value);
    }

    public static Sex findByDesc(String desc){
        for (Sex e : Sex.values()) {
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
        Sex theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}
