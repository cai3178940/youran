package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举【模板文件类型】
 *
 * @author cbb
 * @date 2020/03/08
 */
public enum TemplateFileType {

    /**
     * 普通模板文件
     */
    GENERAL(1, "普通模板文件"),
    /**
     * 抽象模板文件
     */
    ABSTRACTED(2, "抽象模板文件"),
    /**
     * 二进制模板文件
     */
    BINARY(3, "二进制模板文件"),
    /**
     * 目录渲染文件
     */
    PARENT_PATH(4, "目录渲染文件"),
    /**
     * 文件名渲染文件
     */
    FILENAME(5, "文件名渲染文件"),

    ;


    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "1,2,3,4,5";
    private static final Map<Integer, TemplateFileType> LOOKUP = new HashMap<>();

    static {
        for (TemplateFileType e : TemplateFileType.values()) {
            LOOKUP.put(e.value, e);
        }
    }

    private final Integer value;
    private final String desc;


    TemplateFileType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static TemplateFileType find(Integer value) {
        return LOOKUP.get(value);
    }

    public static TemplateFileType findByDesc(String desc) {
        for (TemplateFileType e : TemplateFileType.values()) {
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
        TemplateFileType theEnum = find(value);
        return theEnum != null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}

