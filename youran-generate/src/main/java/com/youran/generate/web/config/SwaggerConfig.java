package com.youran.generate.web.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Title: swagger配置引入
 * Description:
 * Author: cbb
 * Create Time: 2018/1/16 09:32
 */
@EnableSwagger2Doc
@Configuration
@ConditionalOnProperty(value = "swagger.enable")
public class SwaggerConfig {
}
