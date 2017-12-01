<#include "/common.ftl">
package ${packageName};

import ${packageName}.web.rest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

<@classCom "合并测试类"></@classCom>
@RunWith(Suite.class)
@Suite.SuiteClasses({
<#list metaEntities as metaEntity>
    ${metaEntity.className}ControllerTest.class,
</#list>
})
public class Main {



}
