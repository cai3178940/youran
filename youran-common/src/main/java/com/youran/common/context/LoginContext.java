package com.youran.common.context;

import java.security.Principal;

/**
 * 用户登录上下文接口
 *
 * @author: cbb
 * @date: 2017/9/18
 */
public interface LoginContext {

    /**
     * 获取当前用户主体信息
     * @return
     */
    Principal getCurrentUserPrincipal();

    /**
     * 获取当前登录用户唯一标识
     *
     * @return
     */
    String getCurrentUser();

}
