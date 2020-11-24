package com.youran.generate;

import com.youran.generate.web.rest.*;
import com.youran.generate.web.rest.team.ProjectTeamControllerTest;
import com.youran.generate.web.rest.team.ProjectTeamMemberControllerTest;
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
    ProjectTeamControllerTest.class,
    ProjectTeamMemberControllerTest.class,
})
public class Main {


}
