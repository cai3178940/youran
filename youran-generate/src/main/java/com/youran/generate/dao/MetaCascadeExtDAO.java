package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title:【级联扩展】数据库操作</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Repository
@Mapper
public interface MetaCascadeExtDAO extends DAO<MetaCascadeExtPO> {

    /**
     * 根据字段id查询【级联扩展】
     * @param fieldId
     * @return
     */
    List<MetaCascadeExtPO> findByFieldId(Integer fieldId) ;

    /**
     * 判断级联字段id是否存在
     * @param fieldId 级联所属字段
     * @param cascadeEntityId 待检测的实体id
     * @param cascadeFieldId 待检测的字段id
     * @param cascadeExtId 需要排除的主键
     * @return
     */
    boolean cascadeFieldIdExists(@Param("fieldId")Integer fieldId,
                                 @Param("cascadeEntityId")Integer cascadeEntityId,
                                 @Param("cascadeFieldId")Integer cascadeFieldId,
                                 @Param("cascadeExtId")Integer cascadeExtId);

}
