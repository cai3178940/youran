package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: 【级联扩展】数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
@Mapper
public interface MetaCascadeExtDAO extends DAO<MetaCascadeExtPO> {

    List<MetaCascadeExtPO> findByFieldId(Integer fieldId) ;
}
