<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("java.lang.annotation.*")/>
<@call this.printClassCom("乐观锁注解")/>
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
package ${this.commonPackage}.optimistic;

<@call this.printImport()/>

${code}
