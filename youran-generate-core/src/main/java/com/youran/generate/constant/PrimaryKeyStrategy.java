package com.youran.generate.constant;


import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【主键策略】
 *
 * @author cbb
 * @date 2019/12/26
 */
public enum PrimaryKeyStrategy {

    /**
     * 无
     */
    NONE(0,"无"),
    /**
     * 数据库自增
     */
    AUTO_INCREMENT(1,"数据库自增"),
    /**
     * 32位UUID
     */
    UUID_32(2,"32位UUID"),
    /**
     * 16位UUID
     */
    UUID_16(3,"16位UUID");


    private final Integer value;
    private final String desc;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "0,1,2,3";

    private static final Map<Integer, PrimaryKeyStrategy> LOOKUP = new HashMap<>();

    static {
        for (PrimaryKeyStrategy e : PrimaryKeyStrategy.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    PrimaryKeyStrategy(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static PrimaryKeyStrategy find(Integer value) {
        return LOOKUP.get(value);
    }

    public static PrimaryKeyStrategy findByDesc(String desc){
        for (PrimaryKeyStrategy e : PrimaryKeyStrategy.values()) {
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
        PrimaryKeyStrategy theEnum = findByDesc(desc);
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
        PrimaryKeyStrategy theEnum = find(value);
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
        PrimaryKeyStrategy theEnum = find(value);
        return theEnum!=null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}
