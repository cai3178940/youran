<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${packageName}.web.rest.*"/>
<@import "org.junit.runner.RunWith"/>
<@import "org.junit.runners.Suite"/>
<@classCom "合并测试类"/>
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
package ${packageName};

<@printImport/>

${code}
