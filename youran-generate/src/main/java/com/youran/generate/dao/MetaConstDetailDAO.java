package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title:【常量值】数据库操作</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Repository
@Mapper
public interface MetaConstDetailDAO extends DAO<MetaConstDetailPO> {

    /**
     * 根据常量id查询常量值列表
     * @param constId
     * @return
     */
    List<MetaConstDetailPO> findByConstId(Integer constId) ;

}
