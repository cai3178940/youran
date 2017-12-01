package com.cbb.sample;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Title: 单元测试抽象类
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApp.class)
public class AbstractTest {

    @Autowired
    protected H2Flusher h2Flusher;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Before
    public void setUp() throws Exception {
        if(!jdbcUrl.startsWith("jdbc:h2:mem:")){
            return;
        }
        h2Flusher.flushDB();
    }


}
