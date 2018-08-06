<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.springframework.boot.context.properties.ConfigurationProperties")/>
<@call this.printClassCom("配置参数类")/>
@ConfigurationProperties(prefix = "${this.packageName}")
public class ${this.projectNameUpper}Properties {


}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.config;

<@call this.printImport()/>

${code}
