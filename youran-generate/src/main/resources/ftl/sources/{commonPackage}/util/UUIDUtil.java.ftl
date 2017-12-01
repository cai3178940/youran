<#include "/common.ftl">
package ${commonPackage}.util;

import java.util.UUID;

<@classCom "获取UUID"></@classCom>
public class UUIDUtil {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String getUUID16() {
        return getUUID().substring(8,24);
    }

}
