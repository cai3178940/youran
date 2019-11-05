package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaIndexPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【索引】数据库操作
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@Repository
@Mapper
public interface MetaIndexDAO extends DAO<MetaIndexPO> {


    /**
     * 根据实体id查询索引列表
     *
     * @param entityId
     * @return
     */
    List<MetaIndexPO> findByEntityId(Integer entityId);
}
