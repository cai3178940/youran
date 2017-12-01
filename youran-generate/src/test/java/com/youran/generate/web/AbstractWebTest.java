package com.youran.generate.web;

import com.youran.generate.AbstractTest;
import com.youran.generate.constant.GenerateConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Title: web单元测试抽象类
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 14:54
 */
@AutoConfigureMockMvc(printOnlyOnFailure=false)
public abstract class AbstractWebTest extends AbstractTest {

    @Autowired
    protected MockMvc restMockMvc;

    @Value(GenerateConst.GENERATE_ROOT_PATH)
    protected String rootPath;

    protected String getRootPath(){
        if(StringUtils.isBlank(rootPath)){
            return "";
        }
        return "/"+rootPath;
    }

}
