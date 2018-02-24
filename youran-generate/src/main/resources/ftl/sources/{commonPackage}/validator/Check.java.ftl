<#include "/common.ftl">
package ${commonPackage}.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

<@classCom "常量类中静态校验方法标记" "标记了Check注解的方法会用在常量校验中"/>
@Target(METHOD)
@Retention(RUNTIME)
public @interface Check {

    String message() default "";

}
