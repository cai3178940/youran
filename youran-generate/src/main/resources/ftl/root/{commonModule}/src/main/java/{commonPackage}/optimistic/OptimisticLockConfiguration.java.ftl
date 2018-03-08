<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "org.slf4j.Logger"/>
<@import "org.slf4j.LoggerFactory"/>
<@import "org.springframework.context.annotation.Bean"/>
<@classCom "乐观锁配置"/>
public class OptimisticLockConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(OptimisticLockConfiguration.class);

    @Bean
    public OptimisticLockAspect optimisticLockAspect(){
        logger.info("创建OptimisticLockAspect");
        return new OptimisticLockAspect();
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.optimistic;

<@printImport/>

${code}
