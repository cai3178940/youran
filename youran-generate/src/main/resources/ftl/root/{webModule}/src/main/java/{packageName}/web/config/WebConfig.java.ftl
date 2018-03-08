<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.xss.WebXSSFilter"/>
<@import "org.springframework.boot.web.servlet.FilterRegistrationBean"/>
<@import "org.springframework.context.annotation.Bean"/>
<@import "org.springframework.context.annotation.Configuration"/>
<@import "java.util.ArrayList"/>
<@import "java.util.List"/>
<@classCom "web相关配置"/>
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

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.web.config;

<@printImport/>

${code}
