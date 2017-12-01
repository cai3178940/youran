package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.springframework.stereotype.Repository;

/**
 * Title:元数据项目数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/24
 */
@Repository
public class MetaProjectDAO extends AbstractDAO<MetaProjectPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaProjectMapper";
    }

}
