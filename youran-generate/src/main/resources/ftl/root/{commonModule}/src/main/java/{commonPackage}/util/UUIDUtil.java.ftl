<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("java.util.UUID")/>
<@call this.printClassCom("获取UUID")/>
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
package ${this.commonPackage}.util;

<@call this.printImport()/>

${code}
