<#include "/common.ftl">
package ${commonPackage}.optimistic;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

<@classCom "启用乐观锁"></@classCom>
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({OptimisticLockConfiguration.class})
public @interface EnableOptimisticLock {



}
