<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "java.lang.annotation.*"/>
<@classCom "乐观锁注解"/>
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OptimisticLock {

    /**
     * 捕获异常
     * @return
     */
    Class<? extends Exception>[] catchType() default {OptimisticException.class};

    /**
     * 异常重试次数
     * @return
     */
    int retry() default 3;

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.optimistic;

<@printImport/>

${code}
