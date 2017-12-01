package com.youran.generate.web.api;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.MetaIndexQueryDTO;
import com.youran.generate.pojo.dto.MetaIndexUpdateDTO;
import com.youran.generate.pojo.vo.MetaIndexListVO;
import com.youran.generate.pojo.vo.MetaIndexShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Title:元数据索引api
 * Description:
 * Author: cbb
 * Create Time:2017/5/16
 */
@Api(tags = "MetaIndexPO", description = "元数据索引")
public interface MetaIndexAPI {

    /**
     * 新增元数据索引
     */
    @ApiOperation(value = "新增元数据索引")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaIndexAddDTO", dataType = "MetaIndexAddDTO", value = "新增索引参数", paramType = "body"),
    })
    ReplyVO<Integer> save(MetaIndexAddDTO metaIndexAddDTO);

    /**
     * 修改元数据索引
     */
    @ApiOperation(value = "修改元数据索引")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaIndexUpdateDTO", dataType = "MetaIndexUpdateDTO", value = "修改索引参数", paramType = "body"),
    })
    ReplyVO<Void> update(MetaIndexUpdateDTO metaIndexUpdateDTO);

    /**
     * 索引列表查询
     */
    @ApiOperation(value = "索引列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaIndexQueryDTO", dataType = "MetaIndexQueryDTO", value = "查询参数", paramType = "body"),
    })
    ReplyVO<List<MetaIndexListVO>> list(MetaIndexQueryDTO metaIndexQueryDTO);

    /**
     * 查看索引详情
     */
    @ApiOperation(value = "查看索引详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "indexId", dataType = "int", value = "索引id", paramType = "path"),
    })
    ReplyVO<MetaIndexShowVO> show(Integer indexId);

    /**
     * 删除索引
     */
    @ApiOperation(value = "删除索引")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "indexId", dataType = "int", value = "索引id", paramType = "path"),
    })
    ReplyVO<Integer> delete(Integer indexId);

    /**
     * 批量删除索引
     */
    @ApiOperation(value = "批量删除索引")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "indexId", dataType = "int", value = "索引id数组", paramType = "body"),
    })
    ReplyVO<Integer> deleteBatch(Integer[] indexId);


}
