package com.youran.common.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author: cbb
 * @date: 2017/5/20
 */
public class UUIDUtil {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String getUUID16() {
        return getUUID().substring(8, 24);
    }

}
