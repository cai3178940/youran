package com.youran.generate.web.rest.team;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.team.ProjectTeamHelper;
import com.youran.generate.pojo.dto.team.ProjectTeamAddDTO;
import com.youran.generate.pojo.dto.team.ProjectTeamUpdateDTO;
import com.youran.generate.pojo.po.team.ProjectTeamPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 【项目组】单元测试
 *
 * @author cbb
 * @date 2020/11/23
 */
public class ProjectTeamControllerTest extends AbstractWebTest {

    @Autowired
    private ProjectTeamHelper projectTeamHelper;


    /**
     * 新增【项目组】
     */
    @Test
    public void save() throws Exception {
        ProjectTeamAddDTO addDTO = projectTeamHelper.getProjectTeamAddDTO();
        restMockMvc.perform(post(getApiPath() + "/project_team")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    /**
     * 修改【项目组】
     */
    @Test
    public void update() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        ProjectTeamUpdateDTO updateDTO = projectTeamHelper.getProjectTeamUpdateDTO(projectTeam);
        restMockMvc.perform(put(getApiPath() + "/project_team")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    /**
     * 列表查询【项目组】
     */
    @Test
    public void list() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        restMockMvc.perform(get(getApiPath() + "/project_team"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 查询【项目组】选项列表
     */
    @Test
    public void findOptions() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        restMockMvc.perform(get(getApiPath() + "/project_team/options"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 查看【项目组】详情
     */
    @Test
    public void show() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        restMockMvc.perform(get(getApiPath() + "/project_team/{teamId}", projectTeam.getTeamId()))
            .andExpect(status().isOk());
    }

    /**
     * 删除单个【项目组】
     */
    @Test
    public void del() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        restMockMvc.perform(delete(getApiPath() + "/project_team/{teamId}", projectTeam.getTeamId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    /**
     * 批量删除【项目组】
     */
    @Test
    public void deleteBatch() throws Exception {
        ProjectTeamPO projectTeam = projectTeamHelper.saveProjectTeamExample();
        restMockMvc.perform(delete(getApiPath() + "/project_team")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(projectTeam.getTeamId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
