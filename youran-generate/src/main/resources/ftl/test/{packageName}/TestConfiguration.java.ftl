<#include "/common.ftl">
package ${packageName};

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

<@classCom "测试配置类"></@classCom>
@Configuration
public class TestConfiguration {

    @Bean
    public H2Flusher h2Flusher(JdbcTemplate jdbcTemplate){
        return new H2Flusher(jdbcTemplate,"DB/${projectName}.sql");
    }

}
