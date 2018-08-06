<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("com.spring4all.swagger.EnableSwagger2Doc")/>
<@call this.addImport("org.springframework.boot.autoconfigure.condition.ConditionalOnProperty")/>
<@call this.addImport("org.springframework.context.annotation.Configuration")/>
<@call this.printClassCom("swagger配置开关")/>
@EnableSwagger2Doc
@Configuration
@ConditionalOnProperty(value = "swagger.enable")
public class SwaggerConfig {
}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.config;

<@call this.printImport()/>

${code}
