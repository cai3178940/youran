<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "org.springframework.boot.context.properties.ConfigurationProperties"/>
<@classCom "配置参数类"/>
@ConfigurationProperties(prefix = "${packageName}")
public class ${ProjectName}Properties {


}
</#assign>
<#--开始渲染代码-->
package ${packageName}.config;

<@printImport/>

${code}
