<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.convert.MyCustomDateEditor"/>
<@import "org.springframework.web.bind.WebDataBinder"/>
<@import "org.springframework.web.bind.annotation.InitBinder"/>
<@import "java.util.Date"/>
<@classCom "抽象controller"/>
public abstract class AbstractController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
    }

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.web;

<@printImport/>

${code}
