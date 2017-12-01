package com.youran.common.util;

import java.util.UUID;

/**
 * Title:获取UUID
 * Description:
 * Author: cbb
 * Create Time:2017/5/20 12:59
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
