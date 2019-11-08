package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.UserSettingPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 【用户配置】数据库操作
 *
 * @author cbb
 * @date 2019/11/08
 */
@Repository
@Mapper
public interface UserSettingDAO extends DAO<UserSettingPO> {

    boolean notUnique(@Param("username") String username, @Param("id") Integer id);

    UserSettingPO findByUsername(String username);


}



