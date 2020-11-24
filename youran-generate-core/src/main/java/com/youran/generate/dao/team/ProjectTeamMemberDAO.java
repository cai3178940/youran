package com.youran.generate.dao.team;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.team.ProjectTeamMemberPO;
import com.youran.generate.pojo.qo.team.ProjectTeamMemberQO;
import com.youran.generate.pojo.vo.team.ProjectTeamMemberListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【项目组成员】数据库操作
 *
 * @author cbb
 * @date 2020/11/23
 */
@Repository
@Mapper
public interface ProjectTeamMemberDAO extends DAO<ProjectTeamMemberPO> {

    /**
     * 根据条件查询【项目组成员】列表
     *
     * @param projectTeamMemberQO
     * @return
     */
    List<ProjectTeamMemberListVO> findListByQuery(ProjectTeamMemberQO projectTeamMemberQO);

    int getCountByTeamId(Integer teamId);

    /**
     * 判断用户是否在项目组中
     *
     * @param teamId
     * @param username
     * @return
     */
    boolean checkExist(@Param("teamId") Integer teamId,
                       @Param("username") String username);

    /**
     * 根据用户名查询项目组id
     *
     * @param username
     * @return
     */
    List<Integer> findTeamIdsByUsername(String username);
}



