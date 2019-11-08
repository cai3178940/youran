package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.UserSettingUpdateDTO;
import com.youran.generate.pojo.vo.SystemUserInfoVO;
import com.youran.generate.pojo.vo.UserSettingShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * @author cbb
 * @date 2019/10/23
 */
@Api(tags = "SystemUser")
public interface SystemUserAPI {

    /**
     * 修改【用户配置】
     */
    @ApiOperation(value="修改【用户配置】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userSettingUpdateDTO", dataType = "UserSettingUpdateDTO", value = "修改【用户配置】参数", paramType = "body"),
    })
    ResponseEntity<UserSettingShowVO> updateSetting(UserSettingUpdateDTO userSettingUpdateDTO);


    /**
     * 查看系统用户信息
     */
    @ApiOperation(value = "查看系统用户信息")
    ResponseEntity<SystemUserInfoVO> info();

}
