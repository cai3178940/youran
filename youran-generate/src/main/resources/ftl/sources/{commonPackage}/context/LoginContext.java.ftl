<#include "/common.ftl">
package ${commonPackage}.context;

<@classCom "登录上下文接口"/>
public interface LoginContext {

    /**
     * 获取当前登录用户id
     * @return
     */
    String getCurrentOperatorId();

}
