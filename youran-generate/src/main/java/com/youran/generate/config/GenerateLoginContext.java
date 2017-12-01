package com.youran.generate.config;

import com.youran.common.LoginContext;
import org.springframework.stereotype.Component;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/18 17:30
 */
@Component
public class GenerateLoginContext implements LoginContext {

    @Override
    public String getCurrentOperatorId() {
        return "admin";
    }

}
