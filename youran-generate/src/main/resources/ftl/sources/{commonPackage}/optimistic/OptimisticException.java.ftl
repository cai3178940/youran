<#include "/common.ftl">
package ${commonPackage}.optimistic;

<@classCom "乐观锁异常"></@classCom>
public class OptimisticException extends RuntimeException{

    public OptimisticException() {

    }

    public OptimisticException(String message) {
        super(message);
    }
}
