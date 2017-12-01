<#include "/common.ftl">
package ${commonPackage};

<@classCom "登录上下文接口"></@classCom>
public interface LoginContext {

    /**
     * 获取当前登录用户id
     * @return
     */
    String getCurrentOperatorId();

}
