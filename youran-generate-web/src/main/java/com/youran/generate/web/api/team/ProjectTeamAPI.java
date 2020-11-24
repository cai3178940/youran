package com.youran.generate.web.api.team;

import com.youran.common.pojo.vo.OptionVO;
import com.youran.generate.pojo.dto.team.ProjectTeamAddDTO;
import com.youran.generate.pojo.dto.team.ProjectTeamUpdateDTO;
import com.youran.generate.pojo.qo.team.ProjectTeamOptionQO;
import com.youran.generate.pojo.qo.team.ProjectTeamQO;
import com.youran.generate.pojo.vo.team.ProjectTeamListVO;
import com.youran.generate.pojo.vo.team.ProjectTeamShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【项目组】API
 * <p>swagger接口文档
 *
 * @author cbb
 * @date 2020/11/23
 */
@Api(tags = "【项目组】API")
public interface ProjectTeamAPI {

    /**
     * 新增【项目组】
     */
    @ApiOperation(value="新增【项目组】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectTeamAddDTO", dataType = "ProjectTeamAddDTO", value = "新增【项目组】参数", paramType = "body"),
    })
    ResponseEntity<ProjectTeamShowVO> save(ProjectTeamAddDTO projectTeamAddDTO) throws Exception;

    /**
     * 修改【项目组】
     */
    @ApiOperation(value="修改【项目组】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectTeamUpdateDTO", dataType = "ProjectTeamUpdateDTO", value = "修改【项目组】参数", paramType = "body"),
    })
    ResponseEntity<ProjectTeamShowVO> update(ProjectTeamUpdateDTO projectTeamUpdateDTO);

    /**
     * 列表查询【项目组】
     */
    @ApiOperation(value="列表查询【项目组】")
    ResponseEntity<List<ProjectTeamListVO>> list(ProjectTeamQO projectTeamQO);

    /**
     * 查询【项目组】选项列表
     */
    @ApiOperation(value = "查询【项目组】选项列表")
    ResponseEntity<List<OptionVO<Integer, String>>> findOptions(ProjectTeamOptionQO qo);

    /**
     * 查看【项目组】详情
     */
    @ApiOperation(value="查看【项目组】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "teamId", dataType = "int", value = "【项目组】id", paramType = "path"),
    })
    ResponseEntity<ProjectTeamShowVO> show(Integer teamId);

    /**
     * 删除单个【项目组】
     */
    @ApiOperation(value="删除单个【项目组】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "teamId", dataType = "int", value = "【项目组】id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer teamId);

    /**
     * 批量删除【项目组】
     */
    @ApiOperation(value = "批量删除【项目组】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);

}

