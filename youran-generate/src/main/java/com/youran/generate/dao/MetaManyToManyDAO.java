package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.qo.MetaManyToManyQO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.vo.MetaManyToManyListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title:多对多关联数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaManyToManyDAO extends AbstractDAO<MetaManyToManyPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaManyToManyMapper";
    }

    /**
     * 根据条件查询多对多关联列表
     * @param metaManyToManyQO
     * @return
     */
    public List<MetaManyToManyListVO> findByQuery(MetaManyToManyQO metaManyToManyQO) {
        return sqlSession.selectList(getMybatisNamespace()+".findListByQuery", metaManyToManyQO);
    }

    /**
     * 根据projectId查询列表
     * @param projectId
     * @return
     */
    public List<MetaManyToManyPO> findByProjectId(Integer projectId) {
        return sqlSession.selectList(getMybatisNamespace()+".findByProjectId", projectId);
    }
}
