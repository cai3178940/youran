package com.youran.generate.web.api.team;

import com.youran.generate.pojo.dto.team.ProjectTeamMemberAddDTO;
import com.youran.generate.pojo.qo.team.ProjectTeamMemberQO;
import com.youran.generate.pojo.vo.team.ProjectTeamMemberListVO;
import com.youran.generate.pojo.vo.team.ProjectTeamMemberShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【项目组成员】API
 * <p>swagger接口文档
 *
 * @author cbb
 * @date 2020/11/23
 */
@Api(tags = "【项目组成员】API")
public interface ProjectTeamMemberAPI {

    /**
     * 新增【项目组成员】
     */
    @ApiOperation(value="新增【项目组成员】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectTeamMemberAddDTO", dataType = "ProjectTeamMemberAddDTO", value = "新增【项目组成员】参数", paramType = "body"),
    })
    ResponseEntity<ProjectTeamMemberShowVO> save(ProjectTeamMemberAddDTO projectTeamMemberAddDTO) throws Exception;

    /**
     * 列表查询【项目组成员】
     */
    @ApiOperation(value="列表查询【项目组成员】")
    ResponseEntity<List<ProjectTeamMemberListVO>> list(ProjectTeamMemberQO projectTeamMemberQO);

    /**
     * 删除单个【项目组成员】
     */
    @ApiOperation(value="删除单个【项目组成员】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "【项目组成员】id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer id);

    /**
     * 批量删除【项目组成员】
     */
    @ApiOperation(value = "批量删除【项目组成员】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);

}

