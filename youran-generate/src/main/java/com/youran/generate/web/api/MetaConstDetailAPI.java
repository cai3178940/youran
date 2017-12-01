package com.youran.generate.web.api;

import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailQueryDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Title:元数据常量值api
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:40
 */
@Api(tags = "MetaConstDetailPO", description = "元数据常量值")
public interface MetaConstDetailAPI {

    /**
     * 新增元数据常量值
     */
    @ApiOperation(value = "新增元数据常量值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaConstDetailAddDTO", dataType = "MetaConstDetailAddDTO", value = "新增常量值参数", paramType = "body"),
    })
    ReplyVO<Integer> save(MetaConstDetailAddDTO metaConstDetailAddDTO);

    /**
     * 修改元数据常量值
     */
    @ApiOperation(value = "修改元数据常量值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaConstDetailUpdateDTO", dataType = "MetaConstDetailUpdateDTO", value = "修改常量值参数", paramType = "body"),
    })
    ReplyVO<Void> update(MetaConstDetailUpdateDTO metaConstDetailUpdateDTO);

    /**
     * 分页查询常量值
     */
    @ApiOperation(value = "分页查询常量值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaConstDetailQueryDTO", dataType = "MetaConstDetailQueryDTO", value = "分页查询参数", paramType = "body"),
    })
    ReplyVO<PageVO<MetaConstDetailListVO>> list(MetaConstDetailQueryDTO metaConstDetailQueryDTO);

    /**
     * 查看常量值详情
     */
    @ApiOperation(value = "查看常量值详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "constDetailId", dataType = "int", value = "常量值id", paramType = "path"),
    })
    ReplyVO<MetaConstDetailShowVO> show(Integer constDetailId);

    /**
     * 删除常量值
     */
    @ApiOperation(value = "删除常量值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "constDetailId", dataType = "int", value = "常量值id", paramType = "path"),
    })
    ReplyVO<Integer> delete(Integer constDetailId);

    /**
     * 批量删除常量值
     */
    @ApiOperation(value = "批量删除常量值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "constDetailId", dataType = "int", value = "常量值id数组", paramType = "body"),
    })
    ReplyVO<Integer> deleteBatch(Integer[] constDetailId);


}
