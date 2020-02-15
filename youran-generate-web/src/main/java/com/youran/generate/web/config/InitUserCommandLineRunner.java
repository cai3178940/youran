package com.youran.generate.web.config;

import com.youran.common.util.JsonUtil;
import com.youran.generate.web.context.GenerateLoginContext;
import com.youran.generate.pojo.dto.UserSettingAddDTO;
import com.youran.generate.pojo.po.UserSettingPO;
import com.youran.generate.service.UserSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 数据初始化
 *
 * @author: cbb
 * @date: 11/8/2019 22:13
 */
@Component
@ConditionalOnProperty(value = "youran.default-user-init")
public class InitUserCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitUserCommandLineRunner.class);

    @Autowired
    private UserSettingService userSettingService;

    @Override
    public void run(String... args) throws Exception {
        UserSettingPO userSetting = userSettingService.getUserSettingByUsername(
            GenerateLoginContext.MOCK_LOGIN_USER, false);
        if(userSetting==null){
            UserSettingAddDTO addDTO = new UserSettingAddDTO();
            addDTO.setUsername(GenerateLoginContext.MOCK_LOGIN_USER);
            addDTO.setTemplateEnabled(false);
            LOGGER.info("初始化用户数据：{}", JsonUtil.toJSONString(addDTO));
            userSettingService.save(addDTO);
        }
    }

}
