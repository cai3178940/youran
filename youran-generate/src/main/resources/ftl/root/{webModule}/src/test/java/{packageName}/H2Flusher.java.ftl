<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.util.H2Util")/>
<@call this.addImport("org.h2.engine.Mode")/>
<@call this.addImport("org.springframework.beans.factory.InitializingBean")/>
<@call this.addImport("org.springframework.jdbc.core.JdbcTemplate")/>
<@call this.addImport("java.util.HashMap")/>
<@call this.addImport("java.util.Map")/>
<@call this.printClassCom("H2数据库刷新器")/>
public class H2Flusher implements InitializingBean {

    /**
     * 数据库脚本文件保存路径
     */
    private final Map<String,String> scriptFilePath;

    private final JdbcTemplate jdbcTemplate;

    public H2Flusher(JdbcTemplate jdbcTemplate,String... scriptFiles) {
        this.jdbcTemplate = jdbcTemplate;
        this.scriptFilePath = new HashMap<>();
        for (String scriptFile : scriptFiles) {
            scriptFilePath.put(scriptFile, H2Util.getH2Script(scriptFile));
        }
    }

    /**
     * 刷新H2数据库
     */
    public void flushDB(){
        jdbcTemplate.execute("drop all objects;");
        for (String key : scriptFilePath.keySet()) {
            jdbcTemplate.execute("runscript from '"+ scriptFilePath.get(key)+"'");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 修改H2数据库配置
        Mode mode = Mode.getInstance("MYSQL");
        // 关闭null值自动转0或空串
        mode.convertInsertNullToZero = false;
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName};

<@call this.printImport()/>

${code}
