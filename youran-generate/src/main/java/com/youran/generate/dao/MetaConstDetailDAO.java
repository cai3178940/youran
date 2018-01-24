package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.qo.MetaConstDetailQO;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title:元数据常量值数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaConstDetailDAO extends AbstractDAO<MetaConstDetailPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaConstDetailMapper";
    }

    /**
     * 根据常量id查询常量值列表
     * @param constId
     * @return
     */
    public List<MetaConstDetailPO> findByConstId(Integer constId) {
        return sqlSession.selectList(getMybatisNamespace()+".findByConstId", constId);
    }

    /**
     * 根据条件查询常量值列表
     * @param metaConstDetailQO
     * @return
     */
    public List<MetaConstDetailListVO> findByQuery(MetaConstDetailQO metaConstDetailQO) {
        return sqlSession.selectList(getMybatisNamespace()+".findListByQuery", metaConstDetailQO);
    }
}
