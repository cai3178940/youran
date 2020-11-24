package com.youran.generate.web.rest.team;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.pojo.qo.OptionQO;
import com.youran.common.pojo.vo.OptionVO;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.team.ProjectTeamAddDTO;
import com.youran.generate.pojo.dto.team.ProjectTeamUpdateDTO;
import com.youran.generate.pojo.mapper.team.ProjectTeamMapper;
import com.youran.generate.pojo.po.team.ProjectTeamPO;
import com.youran.generate.pojo.qo.team.ProjectTeamQO;
import com.youran.generate.pojo.vo.team.ProjectTeamListVO;
import com.youran.generate.pojo.vo.team.ProjectTeamShowVO;
import com.youran.generate.service.team.ProjectTeamService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.team.ProjectTeamAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 【项目组】控制器
 *
 * @author cbb
 * @date 2020/11/23
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/project_team")
public class ProjectTeamController extends AbstractController implements ProjectTeamAPI {

    @Autowired
    private ProjectTeamService projectTeamService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProjectTeamShowVO> save(@Valid @RequestBody ProjectTeamAddDTO projectTeamAddDTO) throws Exception {
        ProjectTeamPO projectTeam = projectTeamService.save(projectTeamAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/project_team/" + projectTeam.getTeamId()))
            .body(ProjectTeamMapper.INSTANCE.toShowVO(projectTeam));
    }

    @Override
    @PutMapping
    public ResponseEntity<ProjectTeamShowVO> update(@Valid @RequestBody ProjectTeamUpdateDTO projectTeamUpdateDTO) {
        ProjectTeamPO projectTeam = projectTeamService.update(projectTeamUpdateDTO);
        return ResponseEntity.ok(ProjectTeamMapper.INSTANCE.toShowVO(projectTeam));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProjectTeamListVO>> list(@Valid ProjectTeamQO projectTeamQO) {
        List<ProjectTeamListVO> list = projectTeamService.list(projectTeamQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/options")
    public ResponseEntity<List<OptionVO<Integer, String>>> findOptions(OptionQO<Integer, String> qo) {
        List<OptionVO<Integer, String>> options = projectTeamService.findOptions(qo);
        return ResponseEntity.ok(options);
    }

    @Override
    @GetMapping(value = "/{teamId}")
    public ResponseEntity<ProjectTeamShowVO> show(@PathVariable Integer teamId) {
        ProjectTeamShowVO projectTeamShowVO = projectTeamService.show(teamId);
        return ResponseEntity.ok(projectTeamShowVO);
    }

    @Override
    @DeleteMapping(value = "/{teamId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer teamId) {
        int count = projectTeamService.delete(teamId);
        return ResponseEntity.ok(count);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if(ArrayUtils.isEmpty(id)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = projectTeamService.delete(id);
        return ResponseEntity.ok(count);
    }

}


