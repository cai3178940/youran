package com.cbb.sample.web;

import com.cbb.sample.AbstractTest;
import com.cbb.sample.constant.SampleConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


/**
 * Title: web单元测试抽象类
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */
@AutoConfigureMockMvc(printOnlyOnFailure=false)
public abstract class AbstractWebTest extends AbstractTest {

    @Autowired
    protected MockMvc restMockMvc;

    @Value(SampleConst.SAMPLE_ROOT_PATH)
    protected String rootPath;

    protected String getRootPath(){
        if(StringUtils.isBlank(rootPath)){
            return "";
        }
        return "/"+rootPath;
    }

}
