<#include "/common.ftl">
package ${commonPackage}.dao;

import ${commonPackage}.LoginContext;
import ${commonPackage}.optimistic.OptimisticException;
import ${commonPackage}.pojo.dto.PageQueryDTO;
import ${commonPackage}.pojo.po.AbstractPO;
import ${commonPackage}.pojo.po.Version;
import ${commonPackage}.pojo.vo.PageVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

<@classCom "DAO超类"></@classCom>
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
     * @param dto
     * @return
     */
    public <VO,DTO extends PageQueryDTO> PageVO<VO> findByPage(DTO dto) {
        int count = sqlSession.selectOne(getMybatisNamespace()+".findCountByQuery",dto);
        List<VO> list = sqlSession.selectList(getMybatisNamespace()+".findListByQuery", dto);
        PageVO<VO> pageVO = new PageVO<>(list,dto.getPageNo(),dto.getPageSize(),count);
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
