package com.youran.generate.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字符串格式转换工具类
 *
 * @author cbb
 * @date 2019/11/11
 */
public class SwitchCaseUtil {

    /**
     * 下划线转驼峰
     *
     * @param name
     * @param capFirst
     * @return
     */
    public static String underlineToCamelCase(String name, boolean capFirst) {
        String[] split = StringUtils.split(name, "_");
        String value = Arrays.stream(split)
            .map(s -> StringUtils.capitalize(s.toLowerCase()))
            .collect(Collectors.joining(""));
        if (!capFirst) {
            return StringUtils.uncapitalize(value);
        }
        return value;
    }

    /**
     * 驼峰转下划线
     *
     * @param name
     * @param upCase
     * @return
     */
    public static String camelCaseToSnakeCase(String name, boolean upCase) {
        String[] split = StringUtils.splitByCharacterTypeCamelCase(name);
        Stream<String> stream = Arrays.stream(split);
        if (upCase) {
            stream = stream.map(String::toUpperCase);
        } else {
            stream = stream.map(String::toLowerCase);
        }
        return stream.collect(Collectors.joining("_"));
    }




}
