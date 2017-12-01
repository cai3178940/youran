package com.youran.generate;

import com.youran.common.util.H2Util;
import org.h2.engine.Mode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: 启动运行H2数据库脚本
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */
public class H2Flusher implements InitializingBean {

    private final Map<String,String> scriptFilePath;

    private final JdbcTemplate jdbcTemplate;

    public H2Flusher(JdbcTemplate jdbcTemplate,String... scriptFiles) {
        this.jdbcTemplate = jdbcTemplate;
        this.scriptFilePath = new HashMap<>();
        for (String scriptFile : scriptFiles) {
            scriptFilePath.put(scriptFile, H2Util.getH2Script(scriptFile));
        }
    }

    public void flushDB(){
        jdbcTemplate.execute("drop all objects;");
        for (String key : scriptFilePath.keySet()) {
            jdbcTemplate.execute("runscript from '"+ scriptFilePath.get(key)+"'");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //修改H2数据库配置
        Mode mode = Mode.getInstance("MYSQL");
        mode.convertInsertNullToZero = false; //关闭null值自动转0或空串
        flushDB();
    }

}
