package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import com.youran.generate.pojo.qo.MetaCascadeExtQO;
import com.youran.generate.pojo.vo.MetaCascadeExtListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: 级联扩展数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaCascadeExtDAO extends AbstractDAO<MetaCascadeExtPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaCascadeExtMapper";
    }

    /**
     * 根据条件查询多对多关联列表
     * @param metaCascadeExtQO
     * @return
     */
    public List<MetaCascadeExtListVO> findByQuery(MetaCascadeExtQO metaCascadeExtQO) {
        return sqlSession.selectList(getMybatisNamespace()+".findListByQuery", metaCascadeExtQO);
    }


}
