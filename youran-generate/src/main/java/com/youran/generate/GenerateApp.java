package com.youran.generate;

import com.didispace.swagger.EnableSwagger2Doc;
import com.youran.common.optimistic.EnableOptimisticLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 18:00
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableOptimisticLock
public class GenerateApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GenerateApp.class);
        app.run(args);
    }

    /**
     * 兼容tomcat部署模式
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GenerateApp.class);
    }

}
