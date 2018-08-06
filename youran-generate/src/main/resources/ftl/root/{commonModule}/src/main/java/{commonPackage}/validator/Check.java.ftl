<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("java.lang.annotation.Retention")/>
<@call this.addImport("java.lang.annotation.Target")/>
<@call this.addStaticImport("java.lang.annotation.ElementType.METHOD")/>
<@call this.addStaticImport("java.lang.annotation.RetentionPolicy.RUNTIME")/>
<@call this.printClassCom("常量类中静态校验方法标记" "标记了Check注解的方法会用在常量校验中")/>
@Target(METHOD)
@Retention(RUNTIME)
public @interface Check {

    String message() default "";

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.validator;

<@call this.printImport()/>

${code}
