package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.qo.MetaFieldQO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Title:元数据字段数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Mapper
public interface MetaFieldDAO extends DAO<MetaFieldPO> {

    /**
     * 根据实体id查询字段列表
     * @param entityId
     * @return
     */
    List<MetaFieldPO> findByEntityId(Integer entityId) ;

    /**
     * 查询实体中给定fieldId的字段数量
     * @param entityId
     * @param fieldIdList
     * @return
     */
    int findCount(@Param("entityId")Integer entityId, @Param("fieldIdList")List<Integer> fieldIdList) ;
}
