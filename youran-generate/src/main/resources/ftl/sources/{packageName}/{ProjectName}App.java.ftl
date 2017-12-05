<#include "/common.ftl">
package ${packageName};

import com.didispace.swagger.EnableSwagger2Doc;
import ${commonPackage}.optimistic.EnableOptimisticLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

<@classCom "启动类"></@classCom>
@SpringBootApplication
@EnableSwagger2Doc
@EnableOptimisticLock
public class ${ProjectName}App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(${ProjectName}App.class);
        app.run(args);
    }

    /**
     * 兼容tomcat部署模式
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GenerateApp.class);
    }
}
