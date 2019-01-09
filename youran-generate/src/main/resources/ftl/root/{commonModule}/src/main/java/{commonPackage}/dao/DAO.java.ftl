<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.context.LoginContext")/>
<@call this.addImport("${this.commonPackage}.pojo.po.AbstractPO")/>
<@call this.addImport("${this.commonPackage}.pojo.qo.AbstractQO")/>
<@call this.addImport("${this.commonPackage}.pojo.qo.PageQO")/>
<@call this.addImport("${this.commonPackage}.pojo.vo.AbstractVO")/>
<@call this.addImport("${this.commonPackage}.pojo.vo.PageVO")/>
<@call this.addImport("${this.commonPackage}.util.SpringUtil")/>
<@call this.addImport("java.util.List")/>
<@call this.addImport("java.util.ArrayList")/>
<@call this.printClassCom("DAO父接口")/>
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
        int count = this._update(po);
        if(count > 0){
            po.postUpdate();
        }
        return count;
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
package ${this.commonPackage}.dao;

<@call this.printImport()/>

${code}
