package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title:【多对多关联】数据库操作</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Repository
@Mapper
public interface MetaManyToManyDAO extends DAO<MetaManyToManyPO> {

    /**
     * 根据projectId查询列表
     * @param projectId
     * @return
     */
    List<MetaManyToManyPO> findByProjectId(Integer projectId) ;

    int getCountByEntityId(Integer entityId);

    /**
     * 判断多对多关系是否已经存在
     * @param entityId1 实体1
     * @param entityId2 实体2
     * @param mtmId 排除的多对多id
     * @return
     */
    boolean findManyToManyExists(@Param("entityId1")Integer entityId1,
                                 @Param("entityId2")Integer entityId2,
                                 @Param("mtmId")Integer mtmId);


}
