package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaIndexFieldPO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Title: 【索引字段关联表】操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Mapper
public interface MetaIndexFieldDAO extends DAO<MetaIndexFieldPO> {


    /**
     * 根据索引id查询字段列表
     * @param indexId
     * @return
     */
    List<MetaFieldListVO> findByIndexId(Integer indexId);

    /**
     * 根据索引id查询关联列表
     * @param indexId
     * @return
     */
    List<Integer> findIdsByIndexId(Integer indexId) ;

    /**
     * 批量插入关联关系
     * @param indexId
     * @param fieldIdList
     * @return
     */
    int saveBatch(@Param("indexId")Integer indexId, @Param("fieldIdList")List<Integer> fieldIdList);


}
