<#include "/common.ftl">
<#include "/entity_common.ftl">
<#assign importList=false>
<#assign importMap=false>
<#--定义主体代码-->
<#assign code>
<@classCom "【${title}】数据库操作"></@classCom>
@Repository
public class ${CName}DAO extends AbstractDAO<${CName}PO> {

    @Override
    protected String getMybatisNamespace() {
        return "${packageName}.mapper.${CName}Mapper";
    }

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as entity>
        <#assign importList=true>
        <#assign importMap=true>
        <#assign otherCName=entity.className?capFirst>
        <#assign othercName=entity.className?uncapFirst>
        <#assign otherType=entity.pkField.jfieldType>
        <#assign thePkId=MetadataUtil.getPkAlias(cName,false)>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    public List<${CName}PO> findBy${otherCName}(${otherType} ${otherPkId}) {
        return sqlSession.selectList(getMybatisNamespace()+".findBy${otherCName}", ${otherPkId});
    }

    public int add${otherCName}(${type} ${thePkId},${otherType} ${otherPkId}) {
        Map<String,Object> params = new HashMap<>();
        params.put("${thePkId}",${thePkId});
        params.put("${otherPkId}",${otherPkId});
        return sqlSession.insert(getMybatisNamespace()+".add${otherCName}",params);
    }

    </#list>
</#if>
<#if metaEntity.mtmUnHoldRefers??>
    <#list metaEntity.mtmUnHoldRefers as entity>
        <#assign importList=true>
        <#assign otherCName=entity.className/>
        <#assign otherType=entity.pkField.jfieldType>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    public List<${CName}PO> findBy${otherCName}(${otherType} ${otherPkId}) {
        return sqlSession.selectList(getMybatisNamespace()+".findBy${otherCName}", ${otherPkId});
    }

    </#list>
</#if>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.dao;

import ${packageName}.pojo.po.${CName}PO;
import ${commonPackage}.dao.AbstractDAO;
import org.springframework.stereotype.Repository;

<#if importMap>
import java.util.HashMap;
import java.util.Map;
</#if>
<#if importList>
import java.util.List;
</#if>
${code}
