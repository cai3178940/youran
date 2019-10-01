<#include "/common.ftl">
<#include "/checkFeatureForRest.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.web.rest.*")/>
<@call this.addImport("org.junit.runner.RunWith")/>
<@call this.addImport("org.junit.runners.Suite")/>
<@call this.printClassCom("合并测试类")/>
@RunWith(Suite.class)
@Suite.SuiteClasses({
<#list this.metaEntities as metaEntity>
    <#if getGenRest(metaEntity)>
    ${metaEntity.className}ControllerTest.class,
    </#if>
</#list>
})
public class Main {


}
</#assign>
<#--开始渲染代码-->
package ${this.packageName};

<@call this.printImport()/>

${code}
