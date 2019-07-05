package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.qo.MetaProjectQO;
import com.youran.generate.pojo.vo.MetaProjectListVO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>Title:【项目】api</p>
 * <p>Description:</p>
 * @author: cbb
 * Create Time:2017/5/24
 */
@Api(tags = "MetaProjectPO")
public interface MetaProjectAPI {

    /**
     * 新增项目
     *
     * @param metaProjectAddDTO
     * @return
     */
    @ApiOperation(value = "新增项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaProjectAddDTO", dataType = "MetaProjectAddDTO", value = "新增项目参数", paramType = "body"),
    })
    ResponseEntity<MetaProjectShowVO> save(MetaProjectAddDTO metaProjectAddDTO) throws Exception;

    /**
     * 修改项目
     *
     * @param metaProjectUpdateDTO
     */
    @ApiOperation(value = "修改项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaProjectUpdateDTO", dataType = "MetaProjectUpdateDTO", value = "修改项目参数", paramType = "body"),
    })
    ResponseEntity<MetaProjectShowVO> update(MetaProjectUpdateDTO metaProjectUpdateDTO);

    /**
     * 查询项目列表
     *
     * @param metaProjectQO
     */
    @ApiOperation(value = "查询项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metaProjectQO", dataType = "MetaProjectQO", value = "分页查询参数", paramType = "body"),
    })
    ResponseEntity<List<MetaProjectListVO>> list(MetaProjectQO metaProjectQO);

    /**
     * 查看项目详情
     *
     * @param projectId
     */
    @ApiOperation(value = "查看项目详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "path"),
    })
    ResponseEntity<MetaProjectShowVO> show(Integer projectId);


    /**
     * 删除项目
     *
     * @param projectId
     */
    @ApiOperation(value = "删除项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer projectId);

    /**
     * 批量删除项目
     *
     * @param projectId
     */
    @ApiOperation(value = "批量删除项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] projectId);


}
