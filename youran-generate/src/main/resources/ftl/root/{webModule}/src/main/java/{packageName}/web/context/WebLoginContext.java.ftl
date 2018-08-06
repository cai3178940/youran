<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.context.LoginContext")/>
<@call this.addImport("org.springframework.stereotype.Component")/>
<@call this.printClassCom("web登录用户上下文")/>
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
package ${this.packageName}.web.context;

<@call this.printImport()/>

${code}
