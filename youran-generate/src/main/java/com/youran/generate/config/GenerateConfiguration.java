package com.youran.generate.config;

import com.youran.common.util.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title:spring配置</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/8/24
 */
@Configuration
public class GenerateConfiguration {

    /**
     * 在这配置bean以后会把applicationContext注入到该类
     * @return
     */
    @Bean
    public SpringUtil springUtil(){
        return new SpringUtil();
    }


}
