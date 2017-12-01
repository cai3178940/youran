package com.cbb.sample.dao;

import com.cbb.sample.pojo.po.UserPO;
import com.youran.common.dao.AbstractDAO;
import org.springframework.stereotype.Repository;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/18 17:23
 */
@Repository
public class UserDAO extends AbstractDAO<UserPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.cbb.sample.mapper.UserMapper";
    }

}
