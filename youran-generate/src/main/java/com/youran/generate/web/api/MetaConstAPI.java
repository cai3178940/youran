package com.youran.generate.web.api;

import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstQueryDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.vo.MetaConstListVO;
import com.youran.generate.pojo.vo.MetaConstShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Title:元数据常量api
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:40
 */
@Api(tags = "MetaConstPO", description = "元数据常量")
public interface MetaConstAPI {

    /**
     * 新增元数据常量
     */
    @ApiOperation(value = "新增元数据常量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaConstAddDTO", dataType = "MetaConstAddDTO", value = "新增常量参数", paramType = "body"),
    })
    ReplyVO<Integer> save(MetaConstAddDTO metaConstAddDTO);

    /**
     * 修改元数据常量
     */
    @ApiOperation(value = "修改元数据常量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaConstUpdateDTO", dataType = "MetaConstUpdateDTO", value = "修改常量参数", paramType = "body"),
    })
    ReplyVO<Void> update(MetaConstUpdateDTO metaConstUpdateDTO);

    /**
     * 分页查询常量
     */
    @ApiOperation(value = "分页查询常量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaConstQueryDTO", dataType = "MetaConstQueryDTO", value = "分页查询参数", paramType = "body"),
    })
    ReplyVO<PageVO<MetaConstListVO>> list(MetaConstQueryDTO metaConstQueryDTO);

    /**
     * 查看常量详情
     */
    @ApiOperation(value = "查看常量详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "constId", dataType = "int", value = "常量id", paramType = "path"),
    })
    ReplyVO<MetaConstShowVO> show(Integer constId);

    /**
     * 删除常量
     */
    @ApiOperation(value = "删除常量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "constId", dataType = "int", value = "常量id", paramType = "path"),
    })
    ReplyVO<Integer> delete(Integer constId);

    /**
     * 批量删除常量
     */
    @ApiOperation(value = "批量删除常量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "constId", dataType = "int", value = "常量id数组", paramType = "body"),
    })
    ReplyVO<Integer> deleteBatch(Integer[] constId);
}
