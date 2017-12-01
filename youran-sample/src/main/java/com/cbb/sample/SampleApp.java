package com.cbb.sample;

import com.didispace.swagger.EnableSwagger2Doc;
import com.youran.common.optimistic.EnableOptimisticLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 14:57
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableOptimisticLock
public class SampleApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SampleApp.class);
        app.run(args);
    }

}
