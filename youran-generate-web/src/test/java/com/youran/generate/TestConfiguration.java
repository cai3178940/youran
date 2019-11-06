package com.youran.generate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 单元测试专用配置类
 *
 * @author: cbb
 * @date: 2017/9/19
 */
@Configuration()
public class TestConfiguration {

    /**
     * H2数据库刷新器
     *
     * @param jdbcTemplate
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.url", havingValue = "jdbc:h2:mem:testdb;MODE=MYSQL;DB_CLOSE_DELAY=-1")
    public H2Flusher h2Flusher(JdbcTemplate jdbcTemplate) {
        return new H2Flusher(jdbcTemplate, "DB/generate.sql");
    }

}
