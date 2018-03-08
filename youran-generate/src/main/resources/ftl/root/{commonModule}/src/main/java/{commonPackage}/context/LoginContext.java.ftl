<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "登录上下文接口"/>
public interface LoginContext {

    /**
     * 获取当前登录用户id
     * @return
     */
    String getCurrentOperatorId();

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.context;

<@printImport/>

${code}
