package com.youran.generate.web.rest.team;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.team.ProjectTeamHelper;
import com.youran.generate.help.team.ProjectTeamMemberHelper;
import com.youran.generate.pojo.dto.team.ProjectTeamMemberAddDTO;
import com.youran.generate.pojo.po.team.ProjectTeamMemberPO;
import com.youran.generate.pojo.po.team.ProjectTeamPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 【项目组成员】单元测试
 *
 * @author cbb
 * @date 2020/11/23
 */
public class ProjectTeamMemberControllerTest extends AbstractWebTest {

    @Autowired
    private ProjectTeamHelper projectTeamHelper;
    @Autowired
    private ProjectTeamMemberHelper projectTeamMemberHelper;


    /**
     * 新增【项目组成员】
     */
    @Test
    public void save() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        ProjectTeamMemberAddDTO addDTO = projectTeamMemberHelper.getProjectTeamMemberAddDTO(projectTeam.getTeamId());
        restMockMvc.perform(post(getApiPath() + "/project_team_member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    /**
     * 列表查询【项目组成员】
     */
    @Test
    public void list() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        projectTeamMemberHelper.saveProjectTeamMemberExample(projectTeam.getTeamId());
        restMockMvc.perform(get(getApiPath() + "/project_team_member"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }


    /**
     * 批量删除【项目组成员】
     */
    @Test
    public void deleteBatch() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        ProjectTeamMemberPO projectTeamMember = projectTeamMemberHelper.saveProjectTeamMemberExample(projectTeam.getTeamId());
        restMockMvc.perform(delete(getApiPath() + "/project_team_member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(projectTeamMember.getId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
