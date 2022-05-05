package com.youran.generate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 单元测试抽象类
 *
 * @author: cbb
 * @date: 2017/09/13
 */
@SpringBootTest(classes = GenerateApp.class)
public class AbstractTest {

    @Autowired(required = false)
    protected H2Flusher h2Flusher;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @BeforeEach
    public void setUp() throws Exception {
        if (!jdbcUrl.startsWith("jdbc:h2:mem:")) {
            return;
        }
        if (h2Flusher == null) {
            throw new RuntimeException("请使用H2内存数据库作为数据源");
        }
        // 每次执行单元测试之前都要刷新数据库
        h2Flusher.flushDB();
    }


}
