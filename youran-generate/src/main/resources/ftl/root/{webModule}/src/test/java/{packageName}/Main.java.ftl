<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.web.rest.*")/>
<@call this.addImport("org.junit.runner.RunWith")/>
<@call this.addImport("org.junit.runners.Suite")/>
<@call this.printClassCom("合并测试类")/>
@RunWith(Suite.class)
@Suite.SuiteClasses({
<#list metaEntities as metaEntity>
    ${metaEntity.className}ControllerTest.class,
</#list>
})
public class Main {



}
</#assign>
<#--开始渲染代码-->
package ${this.packageName};

<@call this.printImport()/>

${code}
