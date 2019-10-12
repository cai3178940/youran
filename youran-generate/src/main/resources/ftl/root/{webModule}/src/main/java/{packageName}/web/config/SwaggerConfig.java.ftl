<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("com.spring4all.swagger.EnableSwagger2Doc")/>
<@call this.addImport("org.springframework.boot.autoconfigure.condition.ConditionalOnProperty")/>
<@call this.addImport("org.springframework.context.annotation.Configuration")/>
<@call this.addImport("org.springframework.core.Ordered")/>
<@call this.addImport("org.springframework.http.HttpStatus")/>
<@call this.addImport("org.springframework.web.servlet.config.annotation.ViewControllerRegistry")/>
<@call this.printClassCom("swagger配置开关")/>
@Configuration
public class SwaggerConfig {

    @EnableSwagger2Doc
    @ConditionalOnProperty(value = "swagger.enabled")
    public static class SwaggerEnabledConfig{}

    /**
     * 如果没有开启swagger，则需要手动隐藏swagger-ui.html静态页面
     */
    @Configuration
    @ConditionalOnProperty(value = "swagger.enabled", havingValue = "false", matchIfMissing = true)
<#if this.projectFeature.bootVersion==2>
    <@call this.addImport("org.springframework.web.servlet.config.annotation.WebMvcConfigurer")/>
    public static class SwaggerDisabledConfig implements WebMvcConfigurer {
<#else>
    <@call this.addImport("org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter")/>
    public static class SwaggerDisabledConfig extends WebMvcConfigurerAdapter {
</#if>

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addStatusController("/swagger-ui.html", HttpStatus.NOT_FOUND);
            registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        }

    }

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.config;

<@call this.printImport()/>

${code}
