<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "org.springframework.context.annotation.Bean"/>
<@import "org.springframework.context.annotation.Configuration"/>
<@import "org.springframework.jdbc.core.JdbcTemplate"/>
<@classCom "测试配置类"/>
@Configuration
public class TestConfiguration {

    @Bean
    public H2Flusher h2Flusher(JdbcTemplate jdbcTemplate){
        return new H2Flusher(jdbcTemplate,"DB/${projectName}.sql");
    }

}
</#assign>
<#--开始渲染代码-->
package ${packageName};

<@printImport/>

${code}
