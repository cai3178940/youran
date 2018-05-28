package com.youran.generate;

/**
 * Title: 合并测试类
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */

import com.youran.generate.web.rest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    MetaProjectControllerTest.class,
    MetaEntityControllerTest.class,
    MetaFieldControllerTest.class,
    MetaIndexControllerTest.class,
    MetaConstControllerTest.class,
    MetaConstDetailControllerTest.class,
    MetaManyToManyControllerTest.class,
    MetaCascadeExtControllerTest.class,
    MetaCodeGenControllerTest.class
})
public class Main {



}
