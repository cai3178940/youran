<#include "/abstracted/common.ftl">
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
     * @param id 主键
     * @return 实体PO对象
     */
    PO findById(Object id);

    /**
     * 查询分页结果
     *
     * @param qo 分页查询参数
     * @param <VO> 列表展示结果类型
     * @param <QO> 分页查询参数类型
     * @return 分页展示对象
     */
    default <VO extends AbstractVO, QO extends PageQO> PageVO<VO> findByPage(QO qo) {
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
     * 根据条件查询记录数
     *
     * @param qo 查询参数
     * @param <QO> 查询参数类型
     * @return
     */
    <QO extends PageQO> int findCountByQuery(QO qo);

    /**
     * 根据分页条件查询列表
     *
     * @param qo 查询参数
     * @param <VO> 列表展示结果类型
     * @param <QO> 查询参数类型
     * @return
     */
    <VO extends AbstractVO, QO extends AbstractQO> List<VO> findListByQuery(QO qo);

    /**
     * 执行插入记录
     *
     * @param po 实体PO对象
     * @return 保存成功数量
     *         如果保存成功返回1，失败则返回0
     */
    int _save(PO po);

    /**
     * 先填充附加字段，再插入记录
     *
     * @param po 实体PO对象
     * @return 保存成功数量
     *         如果保存成功返回1，失败则返回0
     */
    default int save(PO po) {
        LoginContext loginContext = SpringUtil.getBean(LoginContext.class);
        po.preInsert(loginContext.getCurrentOperatorId());
        return this._save(po);
    }

    /**
     * 执行修改实体操作
     *
     * @param po 实体PO对象
     * @return 修改成功数量
     *         如果修改成功返回1，失败则返回0
     */
    int _update(PO po);

    /**
     * 修改实体操作
     * <p>先填充附加字段，再修改记录
     *
     * @param po 实体PO对象
     * @return 修改成功数量
     *         如果修改成功返回1，失败则返回0
     */
    default int update(PO po) {
        LoginContext loginContext = SpringUtil.getBean(LoginContext.class);
        po.preUpdate(loginContext.getCurrentOperatorId());
        int count = this._update(po);
        if (count > 0) {
            po.postUpdate();
        }
        return count;
    }

    /**
     * 删除实体
     *
     * @param id 主键
     * @return 删除成功数量
     *         如果删除成功返回1，失败则返回0
     */
    int delete(Object id);

    /**
     * 根据id判断记录是否存在
     *
     * @param id 主键
     * @return 如果存在，则返回true，不存在则返回false
     */
    boolean exist(Object id);

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.dao;

<@call this.printImport()/>

${code}
