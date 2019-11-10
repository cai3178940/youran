<#include "/abstracted/common.ftl">
<#include "/abstracted/mtmCascadeExtsForOppList.ftl">
<#include "/abstracted/mtmCascadeExtsForOppShow.ftl">
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

<#if !this.pageSign>
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
<#list this.fkFields as id,field>
    int getCountBy${field.jfieldName?capFirst}(${field.jfieldType} ${field.jfieldName});

</#list>
<#list this.holds! as otherEntity,mtm>
    <@call this.addImport("java.util.List")/>
    <@call this.addImport("org.apache.ibatis.annotations.Param")/>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign otherType=otherEntity.pkField.jfieldType>
    <#assign theFkId=mtm.getFkAlias(this.entityId,false)>
    <#assign otherFkId=mtm.getFkAlias(otherEntity.entityId,false)>
    int getCountBy${otherCName}(${otherType} ${otherFkId});

    List<${this.classNameUpper}PO> findBy${otherCName}(${otherType} ${otherFkId});

    int add${otherCName}(@Param("${theFkId}")${this.type} ${theFkId},@Param("${otherFkId}")${otherType} ${otherFkId});

    int remove${otherCName}(@Param("${theFkId}")${this.type} ${theFkId},@Param("${otherFkId}")${otherType}[] ${otherFkId});

    int removeAll${otherCName}(${this.type} ${theFkId});

</#list>
<#list this.unHolds! as otherEntity,mtm>
    <@call this.addImport("java.util.List")/>
    <#assign otherCName=otherEntity.className/>
    <#assign otherType=otherEntity.pkField.jfieldType>
    <#assign otherFkId=mtm.getFkAlias(otherEntity.entityId,false)>
    List<${this.classNameUpper}PO> findBy${otherCName}(${otherType} ${otherFkId});

</#list>
<#list this.metaEntity.checkUniqueIndexes as index>
    <@call this.addImport("org.apache.ibatis.annotations.Param")/>
    <#assign suffix=(index_index==0)?string('',''+index_index)>
    <#assign params=''>
    <#list index.fields as field>
        <#assign params+='@Param("'+field.jfieldName+'")'+field.jfieldType+' '+field.jfieldName+', '>
    </#list>
    boolean notUnique${suffix}(${params}@Param("${this.id}")${this.type} ${this.id});

</#list>
<#--为被持有的实体提供级联【列表】查询方法-->
<#list mtmCascadeEntitiesForOppList as otherEntity>
    <#assign mtm=mtmForOppList[otherEntity_index]>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign otherType=otherEntity.pkField.jfieldType>
    <#assign otherFkId=mtm.getFkAlias(otherEntity.entityId,false)>
    <@call this.addImport("${this.packageName}.pojo.vo.${otherCName}ListVO")/>
    List<${otherCName}ListVO.${this.classNameUpper}VO> findVOFor${otherCName}List(${otherType} ${otherFkId});

</#list>
<#--为被持有的实体提供级联【详情】查询方法-->
<#list mtmCascadeEntitiesForOppShow as otherEntity>
    <#assign mtm=mtmForOppShow[otherEntity_index]>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign otherType=otherEntity.pkField.jfieldType>
    <#assign otherFkId=mtm.getFkAlias(otherEntity.entityId,false)>
    <@call this.addImport("${this.packageName}.pojo.vo.${otherCName}ShowVO")/>
    List<${otherCName}ShowVO.${this.classNameUpper}VO> findVOFor${otherCName}Show(${otherType} ${otherFkId});

</#list>
}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.dao;

<@call this.printImport()/>

${code}


