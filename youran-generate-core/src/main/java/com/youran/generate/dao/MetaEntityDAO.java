package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.vo.MetaMtmEntityListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 【实体】数据库操作
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@Repository
@Mapper
public interface MetaEntityDAO extends DAO<MetaEntityPO> {

    /**
     * 查询项目下所有实体id
     *
     * @return
     */
    List<Integer> findIdsByProject(Integer projectId);

    /**
     * 校验类名是否不唯一
     *
     * @return
     */
    boolean classNameNotUnique(@Param("projectId") Integer projectId,
                               @Param("className") String className,
                               @Param("entityId") Integer entityId);

    /**
     * 校验表名是否不唯一
     *
     * @return
     */
    boolean tableNameNotUnique(@Param("projectId") Integer projectId,
                               @Param("tableName") String tableName,
                               @Param("entityId") Integer entityId);

    /**
     * 查询某实体下的多对多关联实体列表
     *
     * @param entityId 实体id
     * @param hold     是否持有引用
     * @return
     */
    List<MetaMtmEntityListVO> findMtmEntityList(@Param("entityId") Integer entityId,
                                                @Param("hold") boolean hold);


    /**
     * 查询某项目的模块列表
     *
     * @param projectId 项目id
     * @return
     */
    List<Map> findModulesByProject(@Param("projectId") Integer projectId);


}
