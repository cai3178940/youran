<#include "/common.ftl">
package ${packageName};

import com.didispace.swagger.EnableSwagger2Doc;
import ${commonPackage}.optimistic.EnableOptimisticLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

<@classCom "启动类"></@classCom>
@SpringBootApplication
@EnableSwagger2Doc
@EnableOptimisticLock
public class ${ProjectName}App {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(${ProjectName}App.class);
        app.run(args);
    }

}
