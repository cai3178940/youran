package com.cbb.sample;

/**
 * Title: 合并测试类
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */

import com.cbb.sample.web.rest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserControllerTest.class,
})
public class Main {



}
