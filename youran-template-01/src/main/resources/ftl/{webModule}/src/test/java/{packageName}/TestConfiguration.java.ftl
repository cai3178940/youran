<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.springframework.context.annotation.Bean")/>
<@call this.addImport("org.springframework.context.annotation.Configuration")/>
<@call this.addImport("org.springframework.jdbc.core.JdbcTemplate")/>
<@call this.printClassCom("测试配置类")/>
@Configuration
public class TestConfiguration {

    @Bean
    public H2Flusher h2Flusher(JdbcTemplate jdbcTemplate){
        return new H2Flusher(jdbcTemplate,"DB/${this.projectName}.sql");
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName};

<@call this.printImport()/>

${code}
