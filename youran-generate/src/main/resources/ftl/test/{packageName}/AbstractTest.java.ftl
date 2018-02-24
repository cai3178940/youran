<#include "/common.ftl">
package ${packageName};

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

<@classCom "单元测试抽象类"/>
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ${ProjectName}App.class)
public class AbstractTest {

    @Autowired(required = false)
    protected H2Flusher h2Flusher;

    @Value("${r'$'}{spring.datasource.url}")
    private String jdbcUrl;

    @Before
    public void setUp() throws Exception {
        if(!jdbcUrl.startsWith("jdbc:h2:mem:")){
            return;
        }
        if(h2Flusher==null){
            throw new RuntimeException("请使用H2内存数据库作为数据源");
        }
        h2Flusher.flushDB();
    }


}
