package com.youran.generate.web.rest;

import com.youran.common.context.LoginContext;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.vo.SystemUserInfoVO;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.SystemUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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

    @Override
    @GetMapping(value = "/info")
    public ResponseEntity<SystemUserInfoVO> info() {
        SystemUserInfoVO vo = new SystemUserInfoVO();
        vo.setUser(loginContext.getCurrentOperatorId());
        vo.setSysVersion(generateProperties.getVersion());
        return ResponseEntity.ok(vo);
    }

}
