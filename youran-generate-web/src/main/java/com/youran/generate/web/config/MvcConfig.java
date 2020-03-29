package com.youran.generate.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域支持
 *
 * @author: cbb
 * @date: 2018/1/16
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
            .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
            .allowCredentials(false).maxAge(3600);
    }

    /**
     * 映射ui资源路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将所有前端资源 /ui/** 访问都映射到classpath:/ui/ 目录下
        registry.addResourceHandler("/ui/**")
            .addResourceLocations("classpath:/ui/")
            // 缓存30天
            .setCachePeriod(2592000);
    }

    /**
     * 设置ui入口页地址映射
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("forward:/ui/index.html");
        registry.addViewController("/").setViewName("forward:/ui/index.html");
        registry.addViewController("/ui").setViewName("forward:/ui/index.html");
        registry.addViewController("/ui/").setViewName("forward:/ui/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
