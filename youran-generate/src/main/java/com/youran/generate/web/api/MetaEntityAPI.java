package com.youran.generate.web.api;

import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.qo.MetaEntityQO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.vo.MetaEntityListVO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Title:元数据实体api
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:40
 */
@Api(tags = "MetaEntityPO", description = "元数据实体")
public interface MetaEntityAPI {

    /**
     * 新增元数据实体
     */
    @ApiOperation(value = "新增元数据实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaEntityAddDTO", dataType = "MetaEntityAddDTO", value = "新增实体参数", paramType = "body"),
    })
    ReplyVO<Integer> save(MetaEntityAddDTO metaEntityAddDTO);

    /**
     * 修改元数据实体
     */
    @ApiOperation(value = "修改元数据实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaEntityUpdateDTO", dataType = "MetaEntityUpdateDTO", value = "修改实体参数", paramType = "body"),
    })
    ReplyVO<Void> update(MetaEntityUpdateDTO metaEntityUpdateDTO);

    /**
     * 分页查询实体
     */
    @ApiOperation(value = "分页查询实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaEntityQO", dataType = "MetaEntityQO", value = "分页查询参数", paramType = "body"),
    })
    ReplyVO<PageVO<MetaEntityListVO>> list(MetaEntityQO metaEntityQO);

    /**
     * 查看实体详情
     */
    @ApiOperation(value = "查看实体详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ReplyVO<MetaEntityShowVO> show(Integer entityId);

    /**
     * 删除实体
     */
    @ApiOperation(value = "删除实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ReplyVO<Integer> delete(Integer entityId);
    /**
     * 批量删除实体
     */
    @ApiOperation(value = "批量删除实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id数组", paramType = "body"),
    })
    ReplyVO<Integer> deleteBatch(Integer[] entityId);


}
