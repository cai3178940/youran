<#include "/common.ftl">
package ${packageName}.config;

import org.springframework.context.annotation.*;

<@classCom "配置类"></@classCom>
@Configuration
public class ${ProjectName}Configuration {


    @Bean
    public ${ProjectName}Properties ${projectName}Properties(){
        return new ${ProjectName}Properties();
    }


}
