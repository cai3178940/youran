package com.youran.generate.web.api;

import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.qo.MetaEntityQO;
import com.youran.generate.pojo.vo.MetaEntityListVO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * <p>Title:【实体】api</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Api(tags = "MetaEntityPO")
public interface MetaEntityAPI {

    /**
     * 新增实体
     */
    @ApiOperation(value = "新增实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaEntityAddDTO", dataType = "MetaEntityAddDTO", value = "新增实体参数", paramType = "body"),
    })
    ResponseEntity<MetaEntityShowVO> save(MetaEntityAddDTO metaEntityAddDTO) throws Exception;

    /**
     * 修改实体
     */
    @ApiOperation(value = "修改实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaEntityUpdateDTO", dataType = "MetaEntityUpdateDTO", value = "修改实体参数", paramType = "body"),
    })
    ResponseEntity<MetaEntityShowVO> update(MetaEntityUpdateDTO metaEntityUpdateDTO);

    /**
     * 分页查询实体
     */
    @ApiOperation(value = "分页查询实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaEntityQO", dataType = "MetaEntityQO", value = "分页查询参数", paramType = "body"),
    })
    ResponseEntity<PageVO<MetaEntityListVO>> list(MetaEntityQO metaEntityQO);

    /**
     * 查看实体详情
     */
    @ApiOperation(value = "查看实体详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ResponseEntity<MetaEntityShowVO> show(Integer entityId);

    /**
     * 删除实体
     */
    @ApiOperation(value = "删除实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer entityId);
    /**
     * 批量删除实体
     */
    @ApiOperation(value = "批量删除实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] entityId);


}
