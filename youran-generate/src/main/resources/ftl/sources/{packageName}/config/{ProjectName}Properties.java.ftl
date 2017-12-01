<#include "/common.ftl">
package ${packageName}.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

<@classCom "配置参数类"></@classCom>
@ConfigurationProperties(prefix = "${packageName}")
public class ${ProjectName}Properties {


}
