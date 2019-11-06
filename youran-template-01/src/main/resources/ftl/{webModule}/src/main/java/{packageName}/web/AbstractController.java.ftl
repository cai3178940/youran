<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.convert.MyCustomDateEditor")/>
<@call this.addImport("org.springframework.web.bind.WebDataBinder")/>
<@call this.addImport("org.springframework.web.bind.annotation.InitBinder")/>
<@call this.addImport("java.util.Date")/>
<@call this.printClassCom("抽象controller")/>
public abstract class AbstractController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web;

<@call this.printImport()/>

${code}
