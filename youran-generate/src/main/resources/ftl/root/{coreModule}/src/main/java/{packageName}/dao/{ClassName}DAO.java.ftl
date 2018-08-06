<#include "/common.ftl">


<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.commonPackage}.dao.DAO")/>
<@call this.addImport("org.apache.ibatis.annotations.Mapper")/>
<@call this.printClassCom("【${this.title}】数据库操作")/>
@Mapper
public interface ${this.classNameUpper}DAO extends DAO<${this.classNameUpper}PO> {

<#if this.pageSign != 1>
    <@call this.addImport("${this.packageName}.pojo.qo.${this.classNameUpper}QO")/>
    <@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
    <@call this.addImport("java.util.List")/>
    /**
     * 根据条件查询【${this.title}】列表
     * @param ${this.className}QO
     * @return
     */
    List<${this.classNameUpper}ListVO> findListByQuery(${this.classNameUpper}QO ${this.className}QO);
</#if>

<#list this.fields as field>
    <#if field.foreignKey==1>
    int getCountBy${field.jfieldName?capFirst}(${field.jfieldType} ${field.jfieldName});
    </#if>
</#list>

<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as entity>
        <@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
        <@call this.addImport("java.util.List")/>
        <@call this.addImport("org.apache.ibatis.annotations.Param")/>
        <#assign otherCName=entity.className?capFirst>
        <#assign othercName=entity.className?uncapFirst>
        <#assign otherType=entity.pkField.jfieldType>
        <#assign thePkId=MetadataUtil.getPkAlias(this.className,false)>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    int getCountBy${otherCName}(${otherType} ${otherPkId});

    List<${this.classNameUpper}PO> findBy${otherCName}(${otherType} ${otherPkId});

    List<${this.classNameUpper}ListVO> findVOBy${otherCName}(${otherType} ${otherPkId});

    int add${otherCName}(@Param("${thePkId}")${this.type} ${thePkId},@Param("${otherPkId}")${otherType} ${otherPkId});

    int remove${otherCName}(@Param("${thePkId}")${this.type} ${thePkId},@Param("${otherPkId}")${otherType}[] ${otherPkId});

    int removeAll${otherCName}(${this.type} ${thePkId});

    </#list>
</#if>
<#if this.metaEntity.mtmUnHoldRefers??>
    <#list this.metaEntity.mtmUnHoldRefers as entity>
        <@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
        <@call this.addImport("java.util.List")/>
        <#assign otherCName=entity.className/>
        <#assign othercName=entity.className?uncapFirst>
        <#assign otherType=entity.pkField.jfieldType>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    List<${this.classNameUpper}PO> findBy${otherCName}(${otherType} ${otherPkId});

    List<${this.classNameUpper}ListVO> findVOBy${otherCName}(${otherType} ${otherPkId});

    </#list>
</#if>
<#list this.metaEntity.checkUniqueIndexes as index>
    <@call this.addImport("org.apache.ibatis.annotations.Param")/>
    <#assign suffix=(index_index==0)?string('',''+index_index)>
    <#assign params=''>
    <#list index.fields as field>
        <#assign params+='@Param("'+field.jfieldName+'")'+field.jfieldType+' '+field.jfieldName+', '>
    </#list>
    boolean notUnique${suffix}(${params}@Param("${this.id}")${this.type} ${this.id});

</#list>

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.dao;

<@call this.printImport()/>

${code}


