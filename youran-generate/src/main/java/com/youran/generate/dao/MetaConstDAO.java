package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.po.MetaConstPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title:元数据常量数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaConstDAO extends AbstractDAO<MetaConstPO> {


    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaConstMapper";
    }

    /**
     * 根据项目id查询常量id列表
     * @param projectId
     * @return
     */
    public List<Integer> findIdsByProject(Integer projectId) {
        return sqlSession.selectList(getMybatisNamespace()+".findIdsByProject", projectId);
    }
}
