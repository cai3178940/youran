<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.context.LoginContext"/>
<@import "${commonPackage}.pojo.po.AbstractPO"/>
<@import "${commonPackage}.pojo.qo.AbstractQO"/>
<@import "${commonPackage}.pojo.qo.PageQO"/>
<@import "${commonPackage}.pojo.vo.AbstractVO"/>
<@import "${commonPackage}.pojo.vo.PageVO"/>
<@import "${commonPackage}.util.SpringUtil"/>
<@import "java.util.List"/>
<@import "java.util.ArrayList"/>
<@classCom "DAO父接口"/>
public interface DAO<PO extends AbstractPO> {

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    PO findById(Object id);

    /**
     * 提供默认分页实现
     * @param qo
     * @return
     */
    default  <VO extends AbstractVO, QO extends PageQO> PageVO<VO> findByPage(QO qo) {
        int count = this.findCountByQuery(qo);
        List<VO> list;
        if (count > 0) {
            list = this.findListByQuery(qo);
        } else {
            list = new ArrayList<>();
        }
        PageVO<VO> pageVO = new PageVO<>(list, qo.getPageNo(), qo.getPageSize(), count);
        return pageVO;
    }


    /**
     * 根据分页条件查询记录数
     * @param qo
     * @param <QO>
     * @return
     */
    <QO extends PageQO> int findCountByQuery(QO qo);

    /**
     * 根据分页条件查询列表
     * @param qo
     * @param <VO>
     * @param <QO>
     * @return
     */
    <VO extends AbstractVO, QO extends AbstractQO> List<VO> findListByQuery(QO qo);


    /**
     * 执行插入记录
     * @param po
     * @return
     */
    int _save(PO po);

    /**
     * 先填充附加字段，再插入记录
     * @param po
     * @return
     */
    default int save(PO po){
        LoginContext loginContext = SpringUtil.getBean(LoginContext.class);
        po.preInsert(loginContext.getCurrentOperatorId());
        return this._save(po);
    }

    /**
     * 执行修改记录
     *
     * @param po
     * @return
     */
    int _update(PO po);

    /**
     * 先填充附加字段，再修改记录
     *
     * @param po
     * @return
     */
    default int update(PO po){
        LoginContext loginContext = SpringUtil.getBean(LoginContext.class);
        po.preUpdate(loginContext.getCurrentOperatorId());
        return this._update(po);
    }


    /**
     * 删除记录
     *
     * @param id
     * @return
     */
    int delete(Object id);

    /**
     * 根据id判断记录是否存在
     *
     * @param id
     * @return
     */
    boolean exist(Object id);

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.dao;

<@printImport/>

${code}
