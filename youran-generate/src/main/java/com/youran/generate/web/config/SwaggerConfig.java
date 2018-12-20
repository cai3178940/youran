package com.youran.generate.web.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Title: swagger配置引入
 * Description:
 * Author: cbb
 * Create Time: 2018/1/16 09:32
 */
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
    public static class SwaggerDisabledConfig implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addStatusController("/swagger-ui.html", HttpStatus.NOT_FOUND);
            registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        }

    }

}
