package com.youran.generate.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.youran.common.convert.MyFastJsonHttpMessageConverter;
import com.youran.common.xss.FastJsonXSSValueFilter;
import com.youran.common.xss.WebXSSFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time: 2018/2/11 16:21
 */
@Configuration
public class WebConfig {


    /**
     * 解决直接返回字符串带引号的bug
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FastJsonHttpMessageConverter4.class)
    public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4() {
        FastJsonHttpMessageConverter4 fastConverter = new MyFastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
            SerializerFeature.PrettyFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.DisableCircularReferenceDetect);
        fastJsonConfig.setSerializeFilters(new FastJsonXSSValueFilter());
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

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
