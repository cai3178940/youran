package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.GenHistoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Title: 【生成历史记录】数据库操作
 * Description:
 * Author: cbb
 * Create Time:2018/3/17 16:27
 */
@Repository
@Mapper
public interface GenHistoryDAO extends DAO<GenHistoryPO> {



}
