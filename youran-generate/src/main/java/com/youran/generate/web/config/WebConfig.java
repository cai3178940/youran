package com.youran.generate.web.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.youran.common.xss.FastJsonXSSValueFilter;
import com.youran.common.xss.WebXSSFilter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
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
    public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4(){
            @Override
            protected void writeInternal(Object obj, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                HttpHeaders headers = outputMessage.getHeaders();
                OutputStream out = outputMessage.getBody();
                if(type!=null && type.equals(String.class)){
                    out.write(obj.toString().getBytes("UTF-8"));
                    return;
                }
                ByteArrayOutputStream outnew = new ByteArrayOutputStream();
                FastJsonConfig fastJsonConfig = this.getFastJsonConfig();
                int len = JSON.writeJSONString(outnew, fastJsonConfig.getCharset(), obj, fastJsonConfig.getSerializeConfig(), fastJsonConfig.getSerializeFilters(), fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, fastJsonConfig.getSerializerFeatures());
                if (fastJsonConfig.isWriteContentLength()) {
                    headers.setContentLength((long) len);
                }

                outnew.writeTo(out);
                outnew.close();
            }

            @Override
            public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                InputStream is = inputMessage.getBody();
                String input = IOUtils.toString(is, "UTF-8");
                if(StringUtils.isNotBlank(input)){
                    input = Jsoup.clean(input, Whitelist.basicWithImages());
                }
                if(type.equals(String.class)){
                    return input;
                }
                FastJsonConfig fastJsonConfig = this.getFastJsonConfig();
                return JSON.parseObject(input, type, fastJsonConfig.getFeatures());
            }
        };
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
