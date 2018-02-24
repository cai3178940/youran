<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${packageName}.AbstractTest"/>
<@import "${packageName}.constant.${ProjectName}Const"/>
<@import "org.apache.commons.lang3.StringUtils"/>
<@import "org.springframework.beans.factory.annotation.Autowired"/>
<@import "org.springframework.beans.factory.annotation.Value"/>
<@import "org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc"/>
<@import "org.springframework.test.web.servlet.MockMvc"/>
<@classCom "web单元测试抽象类"/>
@AutoConfigureMockMvc(printOnlyOnFailure=false)
public abstract class AbstractWebTest extends AbstractTest {

    @Autowired
    protected MockMvc restMockMvc;

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.web;

<@printImport/>

${code}
