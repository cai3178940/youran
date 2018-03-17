package com.youran.generate.web;

import com.youran.generate.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
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

}
