package com.youran.generate;

import com.youran.generate.web.rest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 合并测试类
 *
 * @author: cbb
 * @date: 2017/09/13
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
    ReverseEngineeringControllerTest.class
})
public class Main {


}
