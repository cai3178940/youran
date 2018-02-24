<#include "/common.ftl">
package ${packageName}.web.config;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

<@classCom "swagger配置开关"/>
@EnableSwagger2Doc
@Configuration
@ConditionalOnProperty(value = "swagger.enable")
public class SwaggerConfig {
}
