package com.youran.common.util;

import java.util.UUID;

/**
 * <p>Title:获取UUID</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/20
 */
public class UUIDUtil {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String getUUID16() {
        return getUUID().substring(8,24);
    }

}
