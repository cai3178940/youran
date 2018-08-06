<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("json常量")/>
public class JsonFieldConst {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATETIME_FORMAT_2 = "yyyy-MM-dd HH:mm:ss.SSS";

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.constant;

<@call this.printImport()/>

${code}
