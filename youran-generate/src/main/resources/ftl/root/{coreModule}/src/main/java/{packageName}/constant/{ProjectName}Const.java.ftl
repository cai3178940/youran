<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("系统常量")/>
public class ${this.projectNameUpper}Const {

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.constant;

<@call this.printImport()/>

${code}
