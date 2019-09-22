package com.youran.generate.web;

import com.youran.generate.AbstractTest;
import com.youran.generate.constant.WebConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>Title:web单元测试抽象类</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@AutoConfigureMockMvc(printOnlyOnFailure=false)
public abstract class AbstractWebTest extends AbstractTest {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected MockMvc restMockMvc;

    @Value(WebConst.API_PATH)
    protected String apiPath;

    protected String getApiPath(){
        if(StringUtils.isBlank(apiPath)){
            return "";
        }
        return apiPath;
    }

}
