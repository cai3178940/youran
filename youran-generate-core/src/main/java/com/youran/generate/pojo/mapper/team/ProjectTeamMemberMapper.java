package com.youran.generate.pojo.mapper.team;

import com.youran.generate.pojo.dto.team.ProjectTeamMemberAddDTO;
import com.youran.generate.pojo.po.team.ProjectTeamMemberPO;
import com.youran.generate.pojo.vo.team.ProjectTeamMemberShowVO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     * addDTO映射po列表
     *
     * @param addDTO
     * @return
     */
    default List<ProjectTeamMemberPO> fromAddDTO(ProjectTeamMemberAddDTO addDTO) {
        if(StringUtils.isBlank(addDTO.getUsername())){
            return Collections.emptyList();
        }
        return Arrays.stream(addDTO.getUsername().split(","))
            .map(String::trim)
            .filter(StringUtils::isNotBlank)
            .map(username -> {
                ProjectTeamMemberPO po = new ProjectTeamMemberPO();
                po.setTeamId(addDTO.getTeamId());
                po.setUsername(username);
                return po;
            })
            .collect(Collectors.toList());
    }

    /**
     * po映射showVO
     *
     * @param projectTeamMemberPO
     * @return
     */
    ProjectTeamMemberShowVO toShowVO(ProjectTeamMemberPO projectTeamMemberPO);


}

