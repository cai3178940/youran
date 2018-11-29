<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.xss.JacksonXssDeserializer")/>
<@call this.addImport("${this.commonPackage}.xss.WebXSSFilter")/>
<@call this.addImport("org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer")/>
<@call this.addImport("org.springframework.boot.web.servlet.FilterRegistrationBean")/>
<@call this.addImport("org.springframework.context.annotation.Bean")/>
<@call this.addImport("org.springframework.context.annotation.Configuration")/>
<@call this.addImport("org.springframework.web.servlet.LocaleResolver")/>
<@call this.addImport("org.springframework.web.servlet.config.annotation.InterceptorRegistry")/>
<@call this.addImport("org.springframework.web.servlet.config.annotation.WebMvcConfigurer")/>
<@call this.addImport("org.springframework.web.servlet.i18n.CookieLocaleResolver")/>
<@call this.addImport("org.springframework.web.servlet.i18n.LocaleChangeInterceptor")/>
<@call this.addImport("java.util.ArrayList")/>
<@call this.addImport("java.util.List")/>
<@call this.addImport("java.util.Locale")/>
<@call this.printClassCom("web相关配置")/>
@Configuration
public class WebConfig {

    /**
     * 防止通过parameter传入XSS脚本
     * @return
     */
    @Bean
    public FilterRegistrationBean webXSSFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        WebXSSFilter filter = new WebXSSFilter();
        registrationBean.setFilter(filter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    /**
     * 防止通过body传入XSS脚本
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonXssCustomizer(){
        return jacksonObjectMapperBuilder ->
            jacksonObjectMapperBuilder.deserializerByType(String.class,new JacksonXssDeserializer());
    }


    /**
     * 国际化解析器，默认中文
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setCookieMaxAge(3600);
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }

    /**
     * 配置语言切换拦截器
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
    <#if this.bootVersion==2>
        return new WebMvcConfigurer() {
    <#else>
        <@call this.addImport("org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter")/>
        return new WebMvcConfigurerAdapter() {
    </#if>
            //拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
                // 自定义语言切换参数，默认是locale
                localeChangeInterceptor.setParamName("lang");
                registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
            }
        };
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.config;

<@call this.printImport()/>

${code}
