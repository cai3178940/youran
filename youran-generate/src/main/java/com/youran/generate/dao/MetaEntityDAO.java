package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaEntityPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Title: 【实体】数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Mapper
public interface MetaEntityDAO extends DAO<MetaEntityPO> {

    /**
     * 查询项目下所有实体id
     * @return
     */
    List<Integer> findIdsByProject(Integer projectId) ;

}
