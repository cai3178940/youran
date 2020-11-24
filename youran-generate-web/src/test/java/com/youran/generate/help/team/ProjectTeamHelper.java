package com.youran.generate.help.team;

import com.youran.generate.pojo.dto.team.*;
import com.youran.generate.pojo.po.team.*;
import com.youran.generate.service.team.ProjectTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.team.ProjectTeamExample.*;

@Component
public class ProjectTeamHelper {

    @Autowired
    private ProjectTeamService projectTeamService;

    /**
     * 生成add测试数据
     * @return
     */
    public ProjectTeamAddDTO getProjectTeamAddDTO(){
        ProjectTeamAddDTO dto = new ProjectTeamAddDTO();
        dto.setName(E_NAME);
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public ProjectTeamUpdateDTO getProjectTeamUpdateDTO(ProjectTeamPO projectTeam){
        ProjectTeamUpdateDTO dto = new ProjectTeamUpdateDTO();
        dto.setTeamId(projectTeam.getTeamId());
        dto.setName(projectTeam.getName());
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public ProjectTeamPO saveProjectTeamExample(){
        ProjectTeamAddDTO addDTO = this.getProjectTeamAddDTO();
        return projectTeamService.save(addDTO);
    }



}

