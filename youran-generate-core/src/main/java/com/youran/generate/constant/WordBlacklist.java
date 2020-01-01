package com.youran.generate.constant;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 单词黑名单
 *
 * @author: cbb
 * @date: 2019/10/26
 */
public class WordBlacklist {

    /**
     * java关键词
     */
    public static final String[] keywords = {
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
        "class", "const", "continue", "default", "do", "double", "else",
        "extends", "final", "finally", "float", "for", "goto", "if",
        "implements", "import", "instanceof", "int", "interface",
        "long", "native", "new", "null", "package", "private",
        "protected", "public", "return", "short", "static", "super",
        "switch", "synchronized", "this", "throw", "throws",
        "transient", "try", "void", "volatile", "while", "assert",
        "enum", "strictfp", "true", "false", "var"
    };

    /**
     * 类名黑名单
     */
    public static final String[] classNameBlacklist = {
        "Class","Long","Integer","String","Short","Double","Float",
        "BigDecimal","Char","Byte","Boolean","Date","Index"
    };

    public static boolean isJavaKeyword(String value) {
        return ArrayUtils.contains(keywords, value);
    }

    public static boolean isClassNameBlacklist(String value) {
        return ArrayUtils.contains(classNameBlacklist, value);
    }


}
