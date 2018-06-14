package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Title:元数据常量值数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Mapper
public interface MetaConstDetailDAO extends DAO<MetaConstDetailPO> {

    /**
     * 根据常量id查询常量值列表
     * @param constId
     * @return
     */
    List<MetaConstDetailPO> findByConstId(Integer constId) ;

}
