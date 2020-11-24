package com.youran.generate.pojo.mapper.team;

import com.youran.generate.pojo.dto.team.ProjectTeamMemberAddDTO;
import com.youran.generate.pojo.po.team.ProjectTeamMemberPO;
import com.youran.generate.pojo.vo.team.ProjectTeamMemberShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 【项目组成员】映射
 *
 * @author cbb
 * @date 2020/11/23
 */
@Mapper
public interface ProjectTeamMemberMapper {

    ProjectTeamMemberMapper INSTANCE = Mappers.getMapper(ProjectTeamMemberMapper.class);

    /**
     * addDTO映射po
     *
     * @param projectTeamMemberAddDTO
     * @return
     */
    ProjectTeamMemberPO fromAddDTO(ProjectTeamMemberAddDTO projectTeamMemberAddDTO);

    /**
     * po映射showVO
     *
     * @param projectTeamMemberPO
     * @return
     */
    ProjectTeamMemberShowVO toShowVO(ProjectTeamMemberPO projectTeamMemberPO);


}

