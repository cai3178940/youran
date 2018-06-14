<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.context.LoginContext"/>
<@import "org.springframework.beans.factory.annotation.Autowired"/>
<@classCom "抽象Service"/>
public abstract class AbstractService {

    @Autowired
    protected LoginContext loginContext;

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.service;

<@printImport/>

${code}
