<#include "/common.ftl">
<#include "/entity_common.ftl">
package ${packageName}.dao;

import ${packageName}.pojo.po.${CName}PO;
import ${commonPackage}.dao.AbstractDAO;
import org.springframework.stereotype.Repository;

<@classCom "【${title}】数据库操作"></@classCom>
@Repository
public class ${CName}DAO extends AbstractDAO<${CName}PO> {

    @Override
    protected String getMybatisNamespace() {
        return "${packageName}.mapper.${CName}Mapper";
    }

}
