package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.po.MetaIndexFieldPO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:元数据索引字段关联表操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaIndexFieldDAO extends AbstractDAO<MetaIndexFieldPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaIndexFieldMapper";
    }

    /**
     * 根据索引id查询字段列表
     * @param indexId
     * @return
     */
    public List<MetaFieldListVO> findByIndexId(Integer indexId) {
        return sqlSession.selectList(getMybatisNamespace()+".findByIndexId",indexId);
    }

    /**
     * 根据索引id查询关联列表
     * @param indexId
     * @return
     */
    public List<Integer> findIdsByIndexId(Integer indexId) {
        return sqlSession.selectList(getMybatisNamespace()+".findIdsByIndexId",indexId);
    }

    /**
     * 批量插入关联关系
     * @param indexId
     * @param fieldIdList
     * @return
     */
    public int saveBatch(Integer indexId, List<Integer> fieldIdList) {
        Map<String,Object> param = new HashMap<>();
        param.put("indexId",indexId);
        param.put("fieldIdList",fieldIdList);
        return sqlSession.insert(getMybatisNamespace()+".saveBatch",param);
    }

}
