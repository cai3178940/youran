package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.qo.MetaIndexQO;
import com.youran.generate.pojo.po.MetaIndexPO;
import com.youran.generate.pojo.vo.MetaIndexListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title:元数据索引数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaIndexDAO extends AbstractDAO<MetaIndexPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaIndexMapper";
    }

    /**
     * 根据实体id查询索引列表
     * @param entityId
     * @return
     */
    public List<MetaIndexPO> findByEntityId(Integer entityId) {
        return sqlSession.selectList(getMybatisNamespace()+".findByEntityId", entityId);
    }

    /**
     * 根据条件查询索引列表
     * @param metaIndexQO
     * @return
     */
    public List<MetaIndexListVO> findByQuery(MetaIndexQO metaIndexQO) {
        return sqlSession.selectList(getMybatisNamespace()+".findListByQuery", metaIndexQO);
    }
}
