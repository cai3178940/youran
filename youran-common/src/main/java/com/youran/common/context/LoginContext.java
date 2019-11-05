package com.youran.common.context;

/**
 * 用户登录上下文接口
 *
 * @author: cbb
 * @date: 2017/9/18
 */
public interface LoginContext {

    /**
     * 获取当前登录用户id
     *
     * @return
     */
    String getCurrentOperatorId();

}
