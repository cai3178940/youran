package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.pojo.qo.MetaMtmCascadeExtQO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtListVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title: 【多对多级联扩展】数据库操作</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/09/21
 */
@Repository
@Mapper
public interface MetaMtmCascadeExtDAO extends DAO<MetaMtmCascadeExtPO> {

    /**
     * 根据条件查询【多对多级联扩展】列表
     * @param metaMtmCascadeExtQO
     * @return
     */
    List<MetaMtmCascadeExtListVO> findListByQuery(MetaMtmCascadeExtQO metaMtmCascadeExtQO);


}



