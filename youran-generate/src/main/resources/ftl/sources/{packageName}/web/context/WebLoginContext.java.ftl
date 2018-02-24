<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.context.LoginContext"/>
<@import "org.springframework.stereotype.Component"/>
<@classCom "web登录用户上下文"/>
@Component
public class WebLoginContext implements LoginContext{

    /**
     * 获取当前操作员id
     * @return
     */
    @Override
    public String getCurrentOperatorId() {
        return "admin";
    }

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.web.context;

<@printImport/>

${code}
