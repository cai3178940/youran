package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.po.MetaEntityPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title:元数据实体数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaEntityDAO extends AbstractDAO<MetaEntityPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaEntityMapper";
    }


    /**
     * 查询项目下所有实体id
     * @return
     */
    public List<Integer> findIdsByProject(Integer projectId) {
        return sqlSession.selectList(getMybatisNamespace()+".findIdsByProject", projectId);
    }

    /**
     * 根据名称查询实体
     */
    /*public MetaEntityPO findByName(String entity, Integer projectId) {
        return null;
    }*/
}
