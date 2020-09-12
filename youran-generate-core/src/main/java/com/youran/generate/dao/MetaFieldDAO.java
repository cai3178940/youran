package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaFieldPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【字段】数据库操作
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@Repository
@Mapper
public interface MetaFieldDAO extends DAO<MetaFieldPO> {

    /**
     * 根据实体id查询字段列表
     *
     * @param entityId
     * @return
     */
    List<MetaFieldPO> findByEntityId(Integer entityId);

    /**
     * 查询实体中给定fieldId的字段数量
     *
     * @param entityId
     * @param fieldIdList
     * @return
     */
    int findCount(@Param("entityId") Integer entityId, @Param("fieldIdList") List<Integer> fieldIdList);

    /**
     * 查询实体中的所有字段名
     *
     * @param entityId
     * @return
     */
    List<String> findJFieldNames(Integer entityId);

    /**
     * 查询实体中的所有查询字段名
     *
     * @param entityId
     * @return
     */
    List<String> findJFieldNamesForQuery(Integer entityId);

    /**
     * 查询label标签
     *
     * @return
     */
    List<String> findLabels();

}
