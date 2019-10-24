package com.youran.generate.web.api;

import com.youran.generate.pojo.vo.SystemUserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/23
 */
@Api(tags = "SystemUser")
public interface SystemUserAPI {

    /**
     * 查看系统用户信息
     */
    @ApiOperation(value = "查看系统用户信息")
    ResponseEntity<SystemUserInfoVO> info();

}
