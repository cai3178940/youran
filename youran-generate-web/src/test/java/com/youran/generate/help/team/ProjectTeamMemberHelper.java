package com.youran.generate.help.team;

import com.youran.generate.pojo.dto.team.*;
import com.youran.generate.pojo.po.team.*;
import com.youran.generate.service.team.ProjectTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.team.ProjectTeamMemberExample.*;

@Component
public class ProjectTeamMemberHelper {

    @Autowired
    private ProjectTeamMemberService projectTeamMemberService;

    /**
     * 生成add测试数据
     * @return
     */
    public ProjectTeamMemberAddDTO getProjectTeamMemberAddDTO(Integer teamId){
        ProjectTeamMemberAddDTO dto = new ProjectTeamMemberAddDTO();
        dto.setTeamId(teamId);
        dto.setUsername(E_USERNAME);
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public ProjectTeamMemberPO saveProjectTeamMemberExample(Integer teamId){
        ProjectTeamMemberAddDTO addDTO = this.getProjectTeamMemberAddDTO(teamId);
        return projectTeamMemberService.save(addDTO);
    }



}

