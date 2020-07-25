package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.qo.MetaConstDetailQO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * 【常量值】API
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@Api(tags = "【常量值】API")
public interface MetaConstDetailAPI {

    /**
     * 新增常量值
     */
    @ApiOperation(value = "新增常量值")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaConstDetailAddDTO", dataType = "MetaConstDetailAddDTO", value = "新增常量值参数", paramType = "body"),
    })
    ResponseEntity<MetaConstDetailShowVO> save(MetaConstDetailAddDTO metaConstDetailAddDTO) throws Exception;

    /**
     * 修改常量值
     */
    @ApiOperation(value = "修改常量值")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaConstDetailUpdateDTO", dataType = "MetaConstDetailUpdateDTO", value = "修改常量值参数", paramType = "body"),
    })
    ResponseEntity<MetaConstDetailShowVO> update(MetaConstDetailUpdateDTO metaConstDetailUpdateDTO);

    /**
     * 查询常量值列表
     */
    @ApiOperation(value = "查询常量值列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaConstDetailQO", dataType = "MetaConstDetailQO", value = "查询参数", paramType = "body"),
    })
    ResponseEntity<List<MetaConstDetailListVO>> list(MetaConstDetailQO metaConstDetailQO);

    /**
     * 查询常量值列表
     */
    @ApiOperation(value = "查询常量值列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaConstDetailQO", dataType = "MetaConstDetailQO", value = "查询参数", paramType = "body"),
    })
    ResponseEntity<Map<String,List<MetaConstDetailListVO>>> lists(MetaConstDetailQO metaConstDetailQO);

    /**
     * 查看常量值详情
     */
    @ApiOperation(value = "查看常量值详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "constDetailId", dataType = "int", value = "常量值id", paramType = "path"),
    })
    ResponseEntity<MetaConstDetailShowVO> show(Integer constDetailId);

    /**
     * 删除常量值
     */
    @ApiOperation(value = "删除常量值")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "constDetailId", dataType = "int", value = "常量值id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer constDetailId);

    /**
     * 批量删除常量值
     */
    @ApiOperation(value = "批量删除常量值")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "constDetailId", dataType = "int", value = "常量值id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] constDetailId);


}
