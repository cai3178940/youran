package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.po.GenHistoryPO;
import org.springframework.stereotype.Repository;

/**
 * Title:生成历史记录数据库操作
 * Description:
 * Author: cbb
 * Create Time:2018/3/17 16:27
 */
@Repository
public class GenHistoryDAO extends AbstractDAO<GenHistoryPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.GenHistoryMapper";
    }


}
