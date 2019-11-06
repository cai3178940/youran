<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.optimistic.EnableOptimisticLock")/>
<@call this.addImport("org.springframework.boot.SpringApplication")/>
<@call this.addImport("org.springframework.boot.autoconfigure.SpringBootApplication")/>
<@call this.addImport("org.springframework.boot.builder.SpringApplicationBuilder")/>
<#if this.projectFeature.bootVersion==2>
    <@call this.addImport("org.springframework.boot.web.servlet.support.SpringBootServletInitializer")/>
<#else>
    <@call this.addImport("org.springframework.boot.web.support.SpringBootServletInitializer")/>
</#if>
<@call this.printClassCom("启动类")/>
@SpringBootApplication
@EnableOptimisticLock
public class ${this.projectNameUpper}App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(${this.projectNameUpper}App.class);
        app.run(args);
    }

    /**
     * 兼容tomcat部署模式
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(${this.projectNameUpper}App.class);
    }
}
</#assign>
<#--开始渲染代码-->
package ${this.packageName};

<@call this.printImport()/>

${code}
