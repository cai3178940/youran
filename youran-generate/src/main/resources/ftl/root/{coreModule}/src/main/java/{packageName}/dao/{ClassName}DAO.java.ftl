<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${packageName}.pojo.po.${CName}PO"/>
<@import "${commonPackage}.dao.DAO"/>
<@import "org.apache.ibatis.annotations.Mapper"/>
<@classCom "【${title}】数据库操作"/>
@Mapper
public interface ${CName}DAO extends DAO<${CName}PO> {

<#if pageSign != 1>
    <@import "${packageName}.pojo.qo.${CName}QO"/>
    <@import "${packageName}.pojo.vo.${CName}ListVO"/>
    <@import "java.util.List"/>
    /**
     * 根据条件查询【${title}】列表
     * @param ${cName}QO
     * @return
     */
    List<${CName}ListVO> findListByQuery(${CName}QO ${cName}QO);
</#if>

<#list fields as field>
    <#if field.foreignKey==1>
    int getCountBy${field.jfieldName?capFirst}(${field.jfieldType} ${field.jfieldName});
    </#if>
</#list>

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as entity>
        <@import "${packageName}.pojo.vo.${CName}ListVO"/>
        <@import "java.util.List"/>
        <@import "org.apache.ibatis.annotations.Param"/>
        <#assign otherCName=entity.className?capFirst>
        <#assign othercName=entity.className?uncapFirst>
        <#assign otherType=entity.pkField.jfieldType>
        <#assign thePkId=MetadataUtil.getPkAlias(cName,false)>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    int getCountBy${otherCName}(${otherType} ${otherPkId});

    List<${CName}PO> findBy${otherCName}(${otherType} ${otherPkId});

    List<${CName}ListVO> findVOBy${otherCName}(${otherType} ${otherPkId});

    int add${otherCName}(@Param("${thePkId}")${type} ${thePkId},@Param("${otherPkId}")${otherType} ${otherPkId});

    int remove${otherCName}(@Param("${thePkId}")${type} ${thePkId},@Param("${otherPkId}")${otherType}[] ${otherPkId});

    int removeAll${otherCName}(${type} ${thePkId});

    </#list>
</#if>
<#if metaEntity.mtmUnHoldRefers??>
    <#list metaEntity.mtmUnHoldRefers as entity>
        <@import "${packageName}.pojo.vo.${CName}ListVO"/>
        <@import "java.util.List"/>
        <#assign otherCName=entity.className/>
        <#assign othercName=entity.className?uncapFirst>
        <#assign otherType=entity.pkField.jfieldType>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    List<${CName}PO> findBy${otherCName}(${otherType} ${otherPkId});

    List<${CName}ListVO> findVOBy${otherCName}(${otherType} ${otherPkId});

    </#list>
</#if>
<#list metaEntity.checkUniqueIndexes as index>
    <@import "org.apache.ibatis.annotations.Param"/>
    <#assign suffix=(index_index==0)?string('',''+index_index)>
    <#assign params=''>
    <#list index.fields as field>
        <#assign params+='@Param("'+field.jfieldName+'")'+field.jfieldType+' '+field.jfieldName+', '>
    </#list>
    boolean notUnique${suffix}(${params}@Param("${id}")${type} ${id});

</#list>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.dao;

<@printImport/>

${code}


