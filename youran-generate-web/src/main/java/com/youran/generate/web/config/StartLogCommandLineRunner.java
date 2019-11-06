package com.youran.generate.web.config;

import com.youran.generate.web.util.IpUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 打印启动日志
 *
 * @author: cbb
 * @date: 2018/1/16
 */
@Component
public class StartLogCommandLineRunner implements CommandLineRunner, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(StartLogCommandLineRunner.class);

    private final Environment env;

    @Value("${swagger.enabled:false}")
    private boolean swaggerEnabled;

    public StartLogCommandLineRunner(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "/");
        String applicationName = env.getProperty("spring.application.name", "");
        String profiles = "";
        if (ArrayUtils.isNotEmpty(env.getActiveProfiles())) {
            profiles = Arrays.stream(env.getActiveProfiles()).collect(Collectors.joining(","));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n----------------------------------------------------------\n")
            .append("\t应用【").append(applicationName).append("】已经启动！\n")
            .append("\t激活profile:\t").append(profiles).append("\n")
            .append("\t访问路径:\n")
            .append("\t本地: \thttp://localhost:").append(port).append(contextPath).append("\n")
            .append("\t外部: \thttp://").append(IpUtil.getLocalIp()).append(":").append(port).append(contextPath).append("\n");
        if (swaggerEnabled) {
            sb.append("\t文档:\thttp://").append(IpUtil.getLocalIp()).append(":").append(port).append(contextPath).append("swagger-ui.html");
        }
        sb.append("\n----------------------------------------------------------");
        LOG.info(sb.toString());

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
