<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "java.util.UUID"/>
<@classCom "获取UUID"/>
public class UUIDUtil {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String getUUID16() {
        return getUUID().substring(8,24);
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.util;

<@printImport/>

${code}
