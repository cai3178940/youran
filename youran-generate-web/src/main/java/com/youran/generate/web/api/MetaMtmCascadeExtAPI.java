package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.qo.MetaMtmCascadeExtQO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【多对多级联扩展】API
 * <p> swagger接口文档
 *
 * @author cbb
 * @date 2019/09/21
 */
@Api(tags = "【多对多级联扩展】API")
public interface MetaMtmCascadeExtAPI {

    /**
     * 新增【多对多级联扩展】
     */
    @ApiOperation(value = "新增【多对多级联扩展】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaMtmCascadeExtAddDTO", dataType = "MetaMtmCascadeExtAddDTO", value = "新增【多对多级联扩展】参数", paramType = "body"),
    })
    ResponseEntity<MetaMtmCascadeExtShowVO> save(MetaMtmCascadeExtAddDTO metaMtmCascadeExtAddDTO) throws Exception;

    /**
     * 修改【多对多级联扩展】
     */
    @ApiOperation(value = "修改【多对多级联扩展】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaMtmCascadeExtUpdateDTO", dataType = "MetaMtmCascadeExtUpdateDTO", value = "修改【多对多级联扩展】参数", paramType = "body"),
    })
    ResponseEntity<MetaMtmCascadeExtShowVO> update(MetaMtmCascadeExtUpdateDTO metaMtmCascadeExtUpdateDTO);

    /**
     * 列表查询【多对多级联扩展】
     */
    @ApiOperation(value = "列表查询【多对多级联扩展】")
    ResponseEntity<List<MetaMtmCascadeExtListVO>> list(MetaMtmCascadeExtQO metaMtmCascadeExtQO);

    /**
     * 查看【多对多级联扩展】详情
     */
    @ApiOperation(value = "查看【多对多级联扩展】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "mtmCascadeExtId", dataType = "int", value = "【多对多级联扩展】id", paramType = "path"),
    })
    ResponseEntity<MetaMtmCascadeExtShowVO> show(Integer mtmCascadeExtId);

    /**
     * 删除单个【多对多级联扩展】
     */
    @ApiOperation(value = "删除单个【多对多级联扩展】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "mtmCascadeExtId", dataType = "int", value = "【多对多级联扩展】id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer mtmCascadeExtId);

    /**
     * 批量删除【多对多级联扩展】
     */
    @ApiOperation(value = "批量删除【多对多级联扩展】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);


}

