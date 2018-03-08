<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "com.didispace.swagger.EnableSwagger2Doc"/>
<@import "org.springframework.boot.autoconfigure.condition.ConditionalOnProperty"/>
<@import "org.springframework.context.annotation.Configuration"/>
<@classCom "swagger配置开关"/>
@EnableSwagger2Doc
@Configuration
@ConditionalOnProperty(value = "swagger.enable")
public class SwaggerConfig {
}
</#assign>
<#--开始渲染代码-->
package ${packageName}.web.config;

<@printImport/>

${code}
