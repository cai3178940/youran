package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.GenHistoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 【生成历史记录】数据库操作
 *
 * @author: cbb
 * @date: 2018/3/17
 */
@Repository
@Mapper
public interface GenHistoryDAO extends DAO<GenHistoryPO> {


}
