package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.dto.MetaProjectQueryDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.MetaProjectListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    /**
     * 根据条件查询项目列表
     * @param metaProjectQueryDTO
     * @return
     */
    public List<MetaProjectListVO> findByQuery(MetaProjectQueryDTO metaProjectQueryDTO) {
        return sqlSession.selectList(getMybatisNamespace()+".findListByQuery", metaProjectQueryDTO);
    }
}
