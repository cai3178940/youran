<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.AbstractTest")/>
<@call this.addImport("${this.packageName}.constant.${this.projectNameUpper}Const")/>
<@call this.addImport("org.apache.commons.lang3.StringUtils")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Autowired")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Value")/>
<@call this.addImport("org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc")/>
<@call this.addImport("org.springframework.test.web.servlet.MockMvc")/>
<@call this.printClassCom("web单元测试抽象类")/>
@AutoConfigureMockMvc(printOnlyOnFailure=false)
public abstract class AbstractWebTest extends AbstractTest {

    @Autowired
    protected MockMvc restMockMvc;

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web;

<@call this.printImport()/>

${code}
