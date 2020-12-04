package com.youran.generate.template.function;

import com.youran.generate.util.SwitchCaseUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 通用模板函数
 * <p>作为内置函数用来辅助freemarker模板
 *
 * @author cbb
 * @date 2019/11/11
 */
public class CommonTemplateFunction {

    /**
     * 创建HashSet
     *
     * @return
     */
    public static Set createHashSet() {
        return new LinkedHashSet();
    }

    /**
     * 创建HashMap包装
     *
     * @return
     */
    public static MapWrapper createHashMapWrapper() {
        return new MapWrapper(new LinkedHashMap());
    }

    /**
     * 下划线转驼峰
     *
     * @param name
     * @param capFirst
     * @return
     */
    public static String underlineToCamelCase(String name, boolean capFirst) {
        return SwitchCaseUtil.underlineToCamelCase(name, capFirst);
    }

    /**
     * 驼峰转下划线
     *
     * @param name
     * @param upCase
     * @return
     */
    public static String camelCaseToSnakeCase(String name, boolean upCase) {
        return SwitchCaseUtil.camelCaseToSnakeCase(name, upCase);
    }

    /**
     * 驼峰转短横线
     *
     * @param name
     * @param upCase
     * @return
     */
    public static String camelCaseToKebabCase(String name, boolean upCase) {
        return SwitchCaseUtil.camelCaseToKebabCase(name, upCase);
    }

    /**
     * 首个单词转小写
     *
     * @param name
     * @return
     */
    public static String lowerFirstWord(String name) {
        return SwitchCaseUtil.lowerFirstWord(name);
    }

    /**
     * 转换【备注】展示
     *
     * @param comment
     * @return
     */
    public static String convertCommentDisplay(String comment) {
        if (StringUtils.isBlank(comment)) {
            return "";
        }
        return comment.replaceAll("\'", "\"")
            .replaceAll("\n", "");
    }

    /**
     * 移除最后一个逗号
     *
     * @param content
     * @return
     */
    public static String removeLastComma(String content) {
        int i = content.lastIndexOf(",");
        if (i < 0) {
            return content;
        }
        return content.substring(0, i) + content.substring(i + 1);
    }

    /**
     * 格式化参数注释
     *
     * @param content
     * @return
     */
    public static String formatParamComments(String content) {
        String tag = "@param ";
        String[] lines = content.split("\n");
        List<String[]> splits = new ArrayList<>(lines.length);
        int maxLength = 0;
        for (String line : lines) {
            if (StringUtils.isBlank(line)) {
                continue;
            }
            String[] arr = line.split(tag, 2);
            if (arr.length < 2) {
                continue;
            }
            String[] pair = arr[1].split("\\s+", 2);
            String param = pair[0];
            String comment = pair.length > 1 ? pair[1] : "";
            if (param.length() > maxLength) {
                maxLength = param.length();
            }
            splits.add(new String[]{arr[0], param, comment});
        }
        StringBuilder sb = new StringBuilder();
        for (String[] split : splits) {
            sb.append(split[0])
                .append(tag);
            if (split[2].length() > 0) {
                sb.append(StringUtils.rightPad(split[1], maxLength + 1))
                    .append(split[2]);
            } else {
                sb.append(split[1]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * map包装类
     */
    public static class MapWrapper {

        private Map target;

        public MapWrapper(Map target) {
            this.target = target;
        }

        public Object get(Object key) {
            return target.get(key);
        }


        public void put(Object key, Object value) {
            target.put(key, value);
        }

        public boolean containsKey(Object key) {
            return target.containsKey(key);
        }

        public Map target() {
            return target;
        }
    }

}
