package com.youran.generate.util;

import org.apache.commons.lang3.StringUtils;

public class LabelsUtil {

    public static final String LABEL_SPLIT = ";";
    public static final String KEY_SPLIT = ":";



    /**
     * 判断实体是否包含标签
     * @param key
     * @param labels
     * @return 标签值
     */
    public static String getLabelValue(String key, String labels) {
        String[] split = StringUtils.split(labels, LabelsUtil.LABEL_SPLIT);
        String key1 = StringUtils.trim(key);
        if(null != split) {
            for (String str : split) {
                str = StringUtils.trim(str);
                // 单key形式，等值比较
                if (StringUtils.equals(str, key1)) {
                    return key;
                }
                // key:value形式，分割后比较
                if (StringUtils.contains(str, LabelsUtil.KEY_SPLIT)) {
                    String[] key_value = StringUtils.split(str, LabelsUtil.KEY_SPLIT);
                    if(null != key_value && key_value.length == 2) {
                        String key2 = StringUtils.trim(key_value[0]);
                        if(StringUtils.equals(key1, key2)) {
                            return StringUtils.trim(key_value[1]);
                        }
                    }
                }
            }
        }
        return null;
    }
}
