package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaConstPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: 【常量】数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
@Mapper
public interface MetaConstDAO extends DAO<MetaConstPO> {

    /**
     * 根据项目id查询常量id列表
     * @param projectId
     * @return
     */
    List<Integer> findIdsByProject(Integer projectId) ;

}
