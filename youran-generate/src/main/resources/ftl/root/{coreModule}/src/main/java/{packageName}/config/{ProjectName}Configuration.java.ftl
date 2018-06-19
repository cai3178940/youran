<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.util.SpringUtil"/>
<@import "org.springframework.context.MessageSource"/>
<@import "org.springframework.context.annotation.*"/>
<@import "org.springframework.validation.beanvalidation.LocalValidatorFactoryBean"/>
<@import "javax.validation.Validator"/>
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

    /**
     * 使用自己配置的参数校验器，支持国际化
     * 如果不自定义的话，会由ValidationAutoConfiguration自动注册，不支持国际化
     * @param messageSource 由MessageSourceAutoConfiguration自动注册
     * @return
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
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
