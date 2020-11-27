package com.youran.generate.help.team;

import com.youran.generate.pojo.dto.team.ProjectTeamMemberAddDTO;
import com.youran.generate.pojo.po.team.ProjectTeamMemberPO;
import com.youran.generate.service.team.ProjectTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.team.ProjectTeamMemberExample.E_USERNAME;

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
        return projectTeamMemberService.save(teamId, E_USERNAME);
    }

}

