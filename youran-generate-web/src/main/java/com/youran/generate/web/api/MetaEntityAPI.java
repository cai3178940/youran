package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.qo.MetaEntityQO;
import com.youran.generate.pojo.vo.MetaEntityListPairVO;
import com.youran.generate.pojo.vo.MetaEntityListVO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * 【实体】api
 *
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
     * 修改实体特性
     */
    @ApiOperation(value = "修改实体特性")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
        @ApiImplicitParam(name = "attributes", dataType = "MetaEntityFeatureDTO", value = "特性参数", paramType = "body"),
    })
    ResponseEntity<MetaEntityShowVO> updateFeature(Integer entityId,
                                                   Map<String, Object> attributes);

    /**
     * 查询实体列表
     */
    @ApiOperation(value = "查询实体列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaEntityQO", dataType = "MetaEntityQO", value = "查询参数", paramType = "query"),
    })
    ResponseEntity<List<MetaEntityListVO>> list(MetaEntityQO metaEntityQO);

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

    /**
     * 查询某实体下的多对多关联实体
     */
    @ApiOperation(value = "查询某实体下的多对多关联实体")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ResponseEntity<MetaEntityListPairVO> mtmEntityListPair(Integer entityId);

    /**
     * 获取某实体作为外键关联时的默认外键名(mysql字段名)
     */
    @ApiOperation(value = "获取某实体作为外键关联时的默认外键名(mysql字段名)")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ResponseEntity<String> getDefaultFkFieldNameForSql(Integer entityId);

    @ApiOperation(value = "查询某项目的模块列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
    })
    ResponseEntity<List<String>> findModules(Integer projectId);
}
