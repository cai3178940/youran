package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>Title:【项目】数据库操作</p>
 * <p>Description:</p>
 * @author: cbb
 * Create Time:2017/5/24
 */
@Repository
@Mapper
public interface MetaProjectDAO extends DAO<MetaProjectPO> {


}
