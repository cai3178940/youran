package com.youran.generate.web.context;

import com.youran.common.context.LoginContext;
import com.youran.generate.config.GenerateAuthentication;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * 当前系统的用户登录上下文
 *
 * @author: cbb
 * @date: 2017/9/18
 */
@Component
public class GenerateLoginContext implements LoginContext {

    public static final String MOCK_LOGIN_USER = "admin";

    @Override
    public Principal getCurrentUserPrincipal() {
        // 如果集成了外部单点登录，则从单点登录中获取用户信息，并封装进GenerateAuthentication中
        return new GenerateAuthentication(MOCK_LOGIN_USER);
    }

    @Override
    public String getCurrentUser() {
        GenerateAuthentication authentication = (GenerateAuthentication) getCurrentUserPrincipal();
        if(authentication==null){
            return null;
        }
        return authentication.getName();
    }

}
