package com.youran.generate.web.rest.team;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.team.ProjectTeamMemberAddDTO;
import com.youran.generate.pojo.qo.team.ProjectTeamMemberQO;
import com.youran.generate.pojo.vo.team.ProjectTeamMemberListVO;
import com.youran.generate.service.team.ProjectTeamMemberService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.team.ProjectTeamMemberAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 【项目组成员】控制器
 *
 * @author cbb
 * @date 2020/11/23
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/project_team_member")
public class ProjectTeamMemberController extends AbstractController implements ProjectTeamMemberAPI {

    @Autowired
    private ProjectTeamMemberService projectTeamMemberService;

    @Override
    @PostMapping
    public ResponseEntity<Integer> save(@Valid @RequestBody ProjectTeamMemberAddDTO projectTeamMemberAddDTO) throws Exception {
        int count = projectTeamMemberService.saveBatch(projectTeamMemberAddDTO);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProjectTeamMemberListVO>> list(@Valid ProjectTeamMemberQO projectTeamMemberQO) {
        List<ProjectTeamMemberListVO> list = projectTeamMemberService.list(projectTeamMemberQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if(ArrayUtils.isEmpty(id)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = projectTeamMemberService.delete(id);
        return ResponseEntity.ok(count);
    }

}


