<#include "/common.ftl">
package ${packageName}.web;

import ${packageName}.AbstractTest;
import ${packageName}.constant.${ProjectName}Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


<@classCom "web单元测试抽象类"></@classCom>
@AutoConfigureMockMvc(printOnlyOnFailure=false)
public abstract class AbstractWebTest extends AbstractTest {

    @Autowired
    protected MockMvc restMockMvc;

    @Value(${ProjectName}Const.${projectName?upper_case}_ROOT_PATH)
    protected String rootPath;

    protected String getRootPath(){
        if(StringUtils.isBlank(rootPath)){
            return "";
        }
        return "/"+rootPath;
    }


}
