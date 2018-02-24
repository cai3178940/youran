<#include "/common.ftl">
package ${packageName}.web.context;

import ${commonPackage}.context.LoginContext;
import org.springframework.stereotype.Component;

<@classCom "web登录用户上下文"/>
@Component
public class WebLoginContext implements LoginContext{

    /**
     * 获取当前操作员id
     * @return
     */
    @Override
    public String getCurrentOperatorId() {
        return "admin";
    }

}
