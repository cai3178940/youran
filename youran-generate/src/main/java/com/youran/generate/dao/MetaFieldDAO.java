package com.youran.generate.dao;

import com.youran.common.dao.AbstractDAO;
import com.youran.generate.pojo.dto.MetaFieldQueryDTO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:元数据字段数据库操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 10:27
 */
@Repository
public class MetaFieldDAO extends AbstractDAO<MetaFieldPO> {

    @Override
    protected String getMybatisNamespace() {
        return "com.youran.generate.mapper.MetaFieldMapper";
    }


    /**
     * 根据实体id查询字段列表
     * @param entityId
     * @return
     */
    public List<MetaFieldPO> findByEntityId(Integer entityId) {
        return sqlSession.selectList(getMybatisNamespace()+".findByEntityId", entityId);
    }

    /**
     * 根据条件查询字段列表
     * @param metaFieldQueryDTO
     * @return
     */
    public List<MetaFieldListVO> findByQuery(MetaFieldQueryDTO metaFieldQueryDTO) {
        return sqlSession.selectList(getMybatisNamespace()+".findListByQuery", metaFieldQueryDTO);
    }

    /**
     * 查询实体中给定fieldId的字段数量
     * @param entityId
     * @param fieldIdList
     * @return
     */
    public int findCount(Integer entityId, List<Integer> fieldIdList) {
        Map<String, Object> params = new HashMap<>();
        params.put("entityId", entityId);
        params.put("fieldIdList", fieldIdList);
        return sqlSession.selectOne(getMybatisNamespace()+".findCount",params);
    }
}
