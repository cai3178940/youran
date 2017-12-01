package com.youran.generate;

import com.didispace.swagger.EnableSwagger2Doc;
import com.youran.common.optimistic.EnableOptimisticLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 18:00
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableOptimisticLock
public class GenerateApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GenerateApp.class);
        app.run(args);
    }

}
