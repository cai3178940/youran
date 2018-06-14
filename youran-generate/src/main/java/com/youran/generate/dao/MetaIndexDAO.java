package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaIndexPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Title:元数据索引数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Mapper
public interface MetaIndexDAO extends DAO<MetaIndexPO> {


    /**
     * 根据实体id查询索引列表
     * @param entityId
     * @return
     */
    List<MetaIndexPO> findByEntityId(Integer entityId);
}
