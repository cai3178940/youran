package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【模板上下文类型】
 *
 * @author cbb
 * @date 2019/10/24
 */
public enum ContextType {

    /**
     * 无
     */
    NONE(0, "无"),
    /**
     * 全局
     */
    GLOBAL(1, "全局"),
    /**
     * 实体
     */
    ENTITY(2, "实体"),
    /**
     * 枚举
     */
    CONST(3, "枚举");


    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "0,1,2,3";
    private static final Map<Integer, ContextType> LOOKUP = new HashMap<>();

    static {
        for (ContextType e : ContextType.values()) {
            LOOKUP.put(e.value, e);
        }
    }

    private final Integer value;
    private final String desc;


    ContextType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ContextType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static ContextType findByDesc(String desc) {
        for (ContextType e : ContextType.values()) {
            if (e.getDesc().equals(desc)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(Integer value) {
        ContextType theEnum = find(value);
        return theEnum != null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

