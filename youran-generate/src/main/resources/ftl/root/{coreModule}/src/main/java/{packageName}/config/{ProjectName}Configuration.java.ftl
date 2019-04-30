<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.util.SpringUtil")/>
<@call this.addImport("org.springframework.boot.autoconfigure.condition.ConditionalOnClass")/>
<@call this.addImport("org.springframework.boot.context.properties.ConfigurationProperties")/>
<@call this.addImport("org.springframework.context.MessageSource")/>
<@call this.addImport("org.springframework.context.annotation.*")/>
<@call this.addImport("org.springframework.validation.beanvalidation.LocalValidatorFactoryBean")/>
<@call this.addImport("javax.validation.Validator")/>
<@call this.printClassCom("配置类")/>
@Configuration
@PropertySources(value = @PropertySource("classpath:/config/${this.projectNameSplit}-default.properties"))
public class ${this.projectNameUpper}Configuration {


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
    @ConditionalOnClass(name="javax.el.ELContext")
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Bean
    @ConfigurationProperties(prefix = "${this.packageName}")
    public ${this.projectNameUpper}Properties ${this.projectName}Properties(){
        return new ${this.projectNameUpper}Properties();
    }


}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.config;

<@call this.printImport()/>

${code}
