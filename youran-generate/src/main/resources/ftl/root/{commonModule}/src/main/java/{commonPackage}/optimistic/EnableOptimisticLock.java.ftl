<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.springframework.context.annotation.Import")/>
<@call this.addImport("java.lang.annotation.*")/>
<@call this.printClassCom("启用乐观锁")/>
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({OptimisticLockConfiguration.class})
public @interface EnableOptimisticLock {


}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.optimistic;

<@call this.printImport()/>

${code}
