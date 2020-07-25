package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.qo.MetaConstQO;
import com.youran.generate.pojo.vo.MetaConstListVO;
import com.youran.generate.pojo.vo.MetaConstShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【常量】api
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@Api(tags = "【常量枚举】API")
public interface MetaConstAPI {

    /**
     * 新增常量
     */
    @ApiOperation(value = "新增常量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaConstAddDTO", dataType = "MetaConstAddDTO", value = "新增常量参数", paramType = "body"),
    })
    ResponseEntity<MetaConstShowVO> save(MetaConstAddDTO metaConstAddDTO) throws Exception;

    /**
     * 修改常量
     */
    @ApiOperation(value = "修改常量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaConstUpdateDTO", dataType = "MetaConstUpdateDTO", value = "修改常量参数", paramType = "body"),
    })
    ResponseEntity<MetaConstShowVO> update(MetaConstUpdateDTO metaConstUpdateDTO);

    /**
     * 查询常量列表
     */
    @ApiOperation(value = "查询常量列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaConstQO", dataType = "MetaConstQO", value = "分页查询参数", paramType = "body"),
    })
    ResponseEntity<List<MetaConstListVO>> list(MetaConstQO metaConstQO);

    /**
     * 查看常量详情
     */
    @ApiOperation(value = "查看常量详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "constId", dataType = "int", value = "常量id", paramType = "path"),
    })
    ResponseEntity<MetaConstShowVO> show(Integer constId);

    /**
     * 删除常量
     */
    @ApiOperation(value = "删除常量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "constId", dataType = "int", value = "常量id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer constId);

    /**
     * 批量删除常量
     */
    @ApiOperation(value = "批量删除常量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "constId", dataType = "int", value = "常量id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] constId);
}
