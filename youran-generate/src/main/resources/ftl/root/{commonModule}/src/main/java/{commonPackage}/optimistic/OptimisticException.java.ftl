<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "乐观锁异常"/>
public class OptimisticException extends RuntimeException{

    public OptimisticException() {

    }

    public OptimisticException(String message) {
        super(message);
    }
}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.optimistic;

<@printImport/>

${code}
