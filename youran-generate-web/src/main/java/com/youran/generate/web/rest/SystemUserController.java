package com.youran.generate.web.rest;

import com.youran.common.context.LoginContext;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.UserSettingUpdateDTO;
import com.youran.generate.pojo.mapper.UserSettingMapper;
import com.youran.generate.pojo.po.UserSettingPO;
import com.youran.generate.pojo.vo.SystemUserInfoVO;
import com.youran.generate.pojo.vo.UserSettingShowVO;
import com.youran.generate.service.CodeTemplateService;
import com.youran.generate.service.UserSettingService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.SystemUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 系统用户信息控制器
 *
 * @author cbb
 * @date 2019/10/23
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/system_user")
public class SystemUserController extends AbstractController implements SystemUserAPI {

    @Autowired
    private LoginContext loginContext;
    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private CodeTemplateService codeTemplateService;
    @Autowired
    private UserSettingService userSettingService;

    @Override
    @PutMapping(value = "/setting")
    public ResponseEntity<UserSettingShowVO> updateSetting(@Valid @RequestBody UserSettingUpdateDTO userSettingUpdateDTO) {
        String currentUser = loginContext.getCurrentUser();
        userSettingUpdateDTO.setUsername(currentUser);
        UserSettingPO userSetting = userSettingService.update(userSettingUpdateDTO);
        return ResponseEntity.ok(UserSettingMapper.INSTANCE.toShowVO(userSetting));
    }

    @Override
    @GetMapping(value = "/info")
    public ResponseEntity<SystemUserInfoVO> info() {
        String currentUser = loginContext.getCurrentUser();
        UserSettingPO userSetting = userSettingService.getUserSettingByUsername(currentUser, true);
        SystemUserInfoVO vo = new SystemUserInfoVO();
        vo.setId(userSetting.getId());
        vo.setUsername(currentUser);
        vo.setTemplateEnabled(userSetting.getTemplateEnabled());
        vo.setSysVersion(generateProperties.getVersion());
        vo.setTemplateExists(codeTemplateService.exists());
        return ResponseEntity.ok(vo);
    }

}
