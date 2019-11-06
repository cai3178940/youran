package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 【项目】数据库操作
 *
 * @author: cbb
 * @date: 2017/5/24
 */
@Repository
@Mapper
public interface MetaProjectDAO extends DAO<MetaProjectPO> {


}
