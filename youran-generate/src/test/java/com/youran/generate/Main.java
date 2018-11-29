package com.youran.generate;

import com.youran.generate.web.rest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Title: 合并测试类
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */
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
    MetaCodeGenControllerTest.class,
    ReverseEngineeringControllerTest.class
})
public class Main {



}
