<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("乐观锁异常")/>
public class OptimisticException extends RuntimeException {

    public OptimisticException() {

    }

    public OptimisticException(String message) {
        super(message);
    }
}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.optimistic;

<@call this.printImport()/>

${code}
