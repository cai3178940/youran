<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "org.springframework.context.annotation.*"/>
<@classCom "配置类"/>
@Configuration
public class ${ProjectName}Configuration {


    @Bean
    public ${ProjectName}Properties ${projectName}Properties(){
        return new ${ProjectName}Properties();
    }


}
</#assign>
<#--开始渲染代码-->
package ${packageName}.config;

<@printImport/>

${code}
