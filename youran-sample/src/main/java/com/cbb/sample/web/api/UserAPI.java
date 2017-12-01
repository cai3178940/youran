package com.cbb.sample.web.api;

import com.cbb.sample.pojo.dto.UserAddDTO;
import com.cbb.sample.pojo.dto.UserQueryDTO;
import com.cbb.sample.pojo.dto.UserUpdateDTO;
import com.cbb.sample.pojo.vo.UserListVO;
import com.cbb.sample.pojo.vo.UserShowVO;
import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 15:00
 */
public interface UserAPI {

    @ApiOperation(value="新增【用户】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userAddDTO", dataType = "UserAddDTO", value = "新增【用户】参数", paramType = "body"),
    })
    ReplyVO<Integer> save(UserAddDTO userAddDTO);

    @ApiOperation(value="修改【用户】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userUpdateDTO", dataType = "UserUpdateDTO", value = "修改【用户】参数", paramType = "body"),
    })
    ReplyVO<Void> update(UserUpdateDTO userUpdateDTO);

    @ApiOperation(value="分页查询【用户】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userQueryDTO", dataType = "UserQueryDTO", value = "分页查询【用户】参数", paramType = "body"),
    })
    ReplyVO<PageVO<UserListVO>> list(UserQueryDTO userQueryDTO);

    @ApiOperation(value="查看【用户】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "Integer", value = "【用户】id", paramType = "path"),
    })
    ReplyVO<UserShowVO> show(Integer id);

    @ApiOperation(value="删除【用户】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "Integer", value = "【用户】id", paramType = "path"),
    })
    ReplyVO<Integer> delete(Integer id);
}
