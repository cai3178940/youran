<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "系统常量"/>
public class ${ProjectName}Const {

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.constant;

<@printImport/>

${code}
