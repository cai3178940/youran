package com.youran.common.dao;

import com.youran.common.LoginContext;
import com.youran.common.optimistic.OptimisticException;
import com.youran.common.pojo.qo.PageQO;
import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.Version;
import com.youran.common.pojo.vo.PageVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Title: DAO超类
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 15:46
 */
public abstract class AbstractDAO<PO extends AbstractPO> {

    @Autowired
    protected SqlSession sqlSession;

    @Autowired
    protected LoginContext loginContext;

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    public PO findById(Object id) {
        return sqlSession.selectOne(getMybatisNamespace()+".findById",id);
    }

    /**
     * 分页查询
     * @param qo
     * @return
     */
    public <VO, QO extends PageQO> PageVO<VO> findByPage(QO qo) {
        int count = sqlSession.selectOne(getMybatisNamespace()+".findCountByQuery",qo);
        List<VO> list = sqlSession.selectList(getMybatisNamespace()+".findListByQuery", qo);
        PageVO<VO> pageVO = new PageVO<>(list,qo.getPageNo(),qo.getPageSize(),count);
        return pageVO;
    }

    /**
     * 保存记录
     * @param po
     * @return
     */
    public int save(PO po){
        po.preInsert(loginContext.getCurrentOperatorId());
        return sqlSession.insert(getMybatisNamespace()+".save",po);
    }

    /**
     * 修改记录
     * @param po
     * @return
     */
    public int update(PO po) {
        po.preUpdate(loginContext.getCurrentOperatorId());
        int update = sqlSession.update(getMybatisNamespace() + ".update", po);
        if((po instanceof Version) && update<=0){
            throw new OptimisticException("更新操作乐观锁异常");
        }
        return update;
    }

    /**
     * 删除记录
     * @param id
     * @return
     */
    public int delete(Object id) {
        return sqlSession.delete(getMybatisNamespace()+".delete",id);
    }

    /**
     * 根据id判断记录是否存在
     * @param id
     * @return
     */
    public boolean exist(Object id){
        int count = sqlSession.selectOne(getMybatisNamespace()+".exist",id);
        return count>0;
    }

    /**
     * 获取mybatis的命名空间
     * @return
     */
    protected abstract String getMybatisNamespace();

}
