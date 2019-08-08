<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.commonPackage}.dao.DAO")/>
<@call this.addImport("org.apache.ibatis.annotations.Mapper")/>
<@call this.addImport("org.springframework.stereotype.Repository")/>
<@call this.printClassCom("【${this.title}】数据库操作")/>
@Repository
@Mapper
public interface ${this.classNameUpper}DAO extends DAO<${this.classNameUpper}PO> {

<#if isFalse(this.pageSign)>
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
    <#if isTrue(field.foreignKey)>
    int getCountBy${field.jfieldName?capFirst}(${field.jfieldType} ${field.jfieldName});

    </#if>
</#list>
<#if this.metaEntity.holds??>
    <#list this.metaEntity.holds as otherEntity,mtm>
        <@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
        <@call this.addImport("java.util.List")/>
        <@call this.addImport("org.apache.ibatis.annotations.Param")/>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign otherType=otherEntity.pkField.jfieldType>
        <#assign theFkId=MetadataUtil.getMtmFkAlias(mtm,this.metaEntity,false)>
        <#assign otherFkId=MetadataUtil.getMtmFkAlias(mtm,otherEntity,false)>
    int getCountBy${otherCName}(${otherType} ${otherFkId});

    List<${this.classNameUpper}PO> findBy${otherCName}(${otherType} ${otherFkId});

    List<${this.classNameUpper}ListVO> findVOBy${otherCName}(${otherType} ${otherFkId});

    int add${otherCName}(@Param("${theFkId}")${this.type} ${theFkId},@Param("${otherFkId}")${otherType} ${otherFkId});

    int remove${otherCName}(@Param("${theFkId}")${this.type} ${theFkId},@Param("${otherFkId}")${otherType}[] ${otherFkId});

    int removeAll${otherCName}(${this.type} ${theFkId});

    </#list>
</#if>
<#if this.metaEntity.unHolds??>
    <#list this.metaEntity.unHolds as otherEntity,mtm>
        <@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
        <@call this.addImport("java.util.List")/>
        <#assign otherCName=otherEntity.className/>
        <#assign otherType=otherEntity.pkField.jfieldType>
        <#assign otherFkId=MetadataUtil.getMtmFkAlias(mtm,otherEntity,false)>
    List<${this.classNameUpper}PO> findBy${otherCName}(${otherType} ${otherFkId});

    List<${this.classNameUpper}ListVO> findVOBy${otherCName}(${otherType} ${otherFkId});

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


