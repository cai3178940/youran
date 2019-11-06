package com.youran.generate.config;

import com.youran.common.context.LoginContext;
import org.springframework.stereotype.Component;

/**
 * 当前系统的用户登录上下文
 *
 * @author: cbb
 * @date: 2017/9/18
 */
@Component
public class GenerateLoginContext implements LoginContext {

    @Override
    public String getCurrentOperatorId() {
        return "admin";
    }

}
