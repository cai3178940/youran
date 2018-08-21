<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("web常量")/>
public class WebConst {

    /**
     * 接口路径前缀
     */
    public static final String API_PATH = "/api";

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.constant;

<@call this.printImport()/>

${code}
