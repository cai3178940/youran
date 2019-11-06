<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.slf4j.Logger")/>
<@call this.addImport("org.slf4j.LoggerFactory")/>
<@call this.addImport("org.springframework.context.annotation.Bean")/>
<@call this.printClassCom("乐观锁配置")/>
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
package ${this.commonPackage}.optimistic;

<@call this.printImport()/>

${code}
