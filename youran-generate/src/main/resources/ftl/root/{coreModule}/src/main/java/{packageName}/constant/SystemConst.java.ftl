<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("系统常量")/>
public class SystemConst {

    // 此处添加业务系统中用到的系统级常量

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.constant;

<@call this.printImport()/>

${code}
