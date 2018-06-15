<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.util.SpringUtil"/>
<@import "org.springframework.context.annotation.*"/>
<@classCom "配置类"/>
@Configuration
@PropertySources(value = @PropertySource("classpath:/config/${projectNameSplit}-default.properties"))
public class ${ProjectName}Configuration {


    /**
     * 在这配置bean以后会把applicationContext注入到该类
     * @return
     */
    @Bean
    public SpringUtil springUtil(){
        return new SpringUtil();
    }


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
