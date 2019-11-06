package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.pojo.qo.MetaMtmCascadeExtQO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【多对多级联扩展】数据库操作
 *
 * @author cbb
 * @date 2019/09/21
 */
@Repository
@Mapper
public interface MetaMtmCascadeExtDAO extends DAO<MetaMtmCascadeExtPO> {

    /**
     * 根据条件查询【多对多级联扩展】列表
     *
     * @param metaMtmCascadeExtQO
     * @return
     */
    List<MetaMtmCascadeExtListVO> findListByQuery(MetaMtmCascadeExtQO metaMtmCascadeExtQO);

    /**
     * 判断级联字段id是否存在
     *
     * @param mtmId           级联所属多对多
     * @param entityId        级联所属实体
     * @param cascadeFieldId  待检测的字段id
     * @param mtmCascadeExtId 需要排除的主键
     * @return
     */
    boolean cascadeFieldIdExists(@Param("mtmId") Integer mtmId,
                                 @Param("entityId") Integer entityId,
                                 @Param("cascadeFieldId") Integer cascadeFieldId,
                                 @Param("mtmCascadeExtId") Integer mtmCascadeExtId);

    /**
     * 根据多对多id和当前实体id查询【多对多级联扩展】
     *
     * @param mtmId
     * @param entityId
     * @return
     */
    List<MetaMtmCascadeExtPO> findByMtmIdAndEntityId(@Param("mtmId") Integer mtmId,
                                                     @Param("entityId") Integer entityId);


    /**
     * 根据级联字段id查询主键列表
     *
     * @param cascadeFieldId
     * @return
     */
    List<Integer> findPkByCascadeFieldId(Integer cascadeFieldId);


}



