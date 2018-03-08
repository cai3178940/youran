<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "org.aspectj.lang.ProceedingJoinPoint"/>
<@import "org.aspectj.lang.Signature"/>
<@import "org.aspectj.lang.annotation.Around"/>
<@import "org.aspectj.lang.annotation.Aspect"/>
<@import "org.aspectj.lang.annotation.Pointcut"/>
<@import "org.aspectj.lang.reflect.MethodSignature"/>
<@import "org.slf4j.Logger"/>
<@import "org.slf4j.LoggerFactory"/>
<@import "org.springframework.core.annotation.Order"/>
<@import "java.lang.reflect.Method"/>
<@classCom "乐观锁AOP"/>
@Aspect
@Order(-1000)
public class OptimisticLockAspect {

    private final static Logger logger = LoggerFactory.getLogger(OptimisticLockAspect.class);

    //拦截任何方法上添加了@Tx注解的方法
    @Pointcut("execution(@${commonPackage}.optimistic.OptimisticLock * *(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public Object doAround(final ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Signature signature = thisJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        final Method targetMethod = methodSignature.getMethod();
        OptimisticLock optimisticLock = targetMethod.getAnnotation(OptimisticLock.class);
        if(optimisticLock ==null){
            throw new RuntimeException("乐观锁aop异常");
        }

        Class<? extends Exception>[] catchTypes = optimisticLock.catchType();
        if(catchTypes==null || catchTypes.length==0){
            throw new RuntimeException("乐观锁aop异常");
        }
        int retry = optimisticLock.retry();
        Object object = tryProceed(thisJoinPoint, catchTypes, retry);
        return object;
    }

    private Object tryProceed(ProceedingJoinPoint thisJoinPoint, Class<? extends Exception>[] catchTypes,int retry) throws Throwable {
        Object object = null;
        try {
            object = thisJoinPoint.proceed();
        } catch (Throwable throwable) {
            if(retry>0) {
                for (Class<? extends Exception> catchType : catchTypes) {
                    if (catchType.isInstance(throwable)) {
                        try {
                            Thread.sleep(100);//睡100毫秒再试
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        logger.warn("乐观锁重试,retry="+retry+",method="+thisJoinPoint.getSignature().getName());
                        return tryProceed(thisJoinPoint,catchTypes,--retry);
                    }
                }
            }
            throw throwable;
        }
        return object;
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.optimistic;

<@printImport/>

${code}
