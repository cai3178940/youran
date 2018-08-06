<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.junit.Before")/>
<@call this.addImport("org.junit.runner.RunWith")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Autowired")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Value")/>
<@call this.addImport("org.springframework.boot.test.context.SpringBootTest")/>
<@call this.addImport("org.springframework.test.context.junit4.SpringRunner")/>
<@call this.printClassCom("单元测试抽象类")/>
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ${this.projectNameUpper}App.class)
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
</#assign>
<#--开始渲染代码-->
package ${this.packageName};

<@call this.printImport()/>

${code}
