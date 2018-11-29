package com.youran.generate.web;

import com.youran.generate.AbstractTest;
import com.youran.generate.constant.GenerateConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Title: web单元测试抽象类
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 14:54
 */
@AutoConfigureMockMvc(printOnlyOnFailure=false)
public abstract class AbstractWebTest extends AbstractTest {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected MockMvc restMockMvc;

    @Value(GenerateConst.API_PATH)
    protected String apiPath;

    protected String getApiPath(){
        if(StringUtils.isBlank(apiPath)){
            return "";
        }
        return "/"+ apiPath;
    }

}
