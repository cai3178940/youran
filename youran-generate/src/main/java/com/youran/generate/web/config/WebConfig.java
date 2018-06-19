package com.youran.generate.web.config;

import com.youran.common.xss.JacksonXssDeserializer;
import com.youran.common.xss.WebXSSFilter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: web配置
 * Description:
 * Author: cbb
 * Create Time: 2018/2/11 16:21
 */
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


}
