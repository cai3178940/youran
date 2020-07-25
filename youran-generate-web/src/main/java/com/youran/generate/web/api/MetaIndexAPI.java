package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.MetaIndexUpdateDTO;
import com.youran.generate.pojo.qo.MetaIndexQO;
import com.youran.generate.pojo.vo.MetaIndexListVO;
import com.youran.generate.pojo.vo.MetaIndexShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【索引】API
 *
 * @author: cbb
 * @date 2017/5/16
 */
@Api(tags = "【索引】API")
public interface MetaIndexAPI {

    /**
     * 新增索引
     */
    @ApiOperation(value = "新增索引")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaIndexAddDTO", dataType = "MetaIndexAddDTO", value = "新增索引参数", paramType = "body"),
    })
    ResponseEntity<MetaIndexShowVO> save(MetaIndexAddDTO metaIndexAddDTO) throws Exception;

    /**
     * 修改索引
     */
    @ApiOperation(value = "修改索引")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaIndexUpdateDTO", dataType = "MetaIndexUpdateDTO", value = "修改索引参数", paramType = "body"),
    })
    ResponseEntity<MetaIndexShowVO> update(MetaIndexUpdateDTO metaIndexUpdateDTO);

    /**
     * 索引列表查询
     */
    @ApiOperation(value = "索引列表查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaIndexQO", dataType = "MetaIndexQO", value = "查询参数", paramType = "body"),
    })
    ResponseEntity<List<MetaIndexListVO>> list(MetaIndexQO metaIndexQO);

    /**
     * 查看索引详情
     */
    @ApiOperation(value = "查看索引详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "indexId", dataType = "int", value = "索引id", paramType = "path"),
    })
    ResponseEntity<MetaIndexShowVO> show(Integer indexId);

    /**
     * 删除索引
     */
    @ApiOperation(value = "删除索引")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "indexId", dataType = "int", value = "索引id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer indexId);

    /**
     * 批量删除索引
     */
    @ApiOperation(value = "批量删除索引")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "indexId", dataType = "int", value = "索引id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] indexId);


    /**
     * 移除索引字段
     */
    @ApiOperation(value = "批量删除索引")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "indexId", dataType = "int", value = "索引id", paramType = "path"),
        @ApiImplicitParam(name = "fieldIds", dataType = "int", value = "字段id数组", paramType = "body"),
    })
    ResponseEntity<Integer> removeField(Integer indexId, List<Integer> fieldIds);
}
