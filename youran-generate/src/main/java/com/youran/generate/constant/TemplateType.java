package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【模板类型】
 *
 * @author cbb
 * @date 2019/10/24
 */
public enum TemplateType {

    /**
     * 后端
     */
    BACK_END(1, "后端"),
    /**
     * 前端
     */
    FRONT_END(2, "前端");


    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2";
    private static final Map<Integer, TemplateType> LOOKUP = new HashMap<>();

    static {
        for (TemplateType e : TemplateType.values()) {
            LOOKUP.put(e.value, e);
        }
    }

    private final Integer value;
    private final String desc;


    TemplateType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static TemplateType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static TemplateType findByDesc(String desc) {
        for (TemplateType e : TemplateType.values()) {
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
        TemplateType theEnum = find(value);
        return theEnum != null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

