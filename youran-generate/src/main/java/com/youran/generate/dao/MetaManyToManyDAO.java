package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import org.apache.ibatis.annotations.Mapper;
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
}
