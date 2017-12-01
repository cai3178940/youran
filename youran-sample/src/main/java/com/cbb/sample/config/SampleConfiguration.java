package com.cbb.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 15:04
 */
@Configuration
public class SampleConfiguration {

    @Bean
    public SampleProperties sampleProperties(){
        return new SampleProperties();
    }


}
