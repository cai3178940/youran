<#include "/common.ftl">
package ${packageName}.config;

import ${commonPackage}.LoginContext;
import org.springframework.stereotype.Component;

<@classCom "登录用户上下文"></@classCom>
@Component
public class ${ProjectName}LoginContext implements LoginContext{

    /**
     * 获取当前操作员id
     * @return
     */
    @Override
    public String getCurrentOperatorId() {
        return "admin";
    }

}
