<#-- mybatis sql模板专用 -->
<#include "/common.ftl">

<#-- 生成if不为空条件内容，支持字段别名-->
<#function ifNotEmptyConditionWithAlias alias field>
    <#if field.queryType==QueryType.IN>
        <#return "${alias} != null and ${alias}.size() >0 " >
    <#elseIf field.jfieldType==JFieldType.STRING.getJavaType()>
        <#return "${alias} != null and ${alias} !='' " >
    <#else>
        <#return "${alias} != null " >
    </#if>
</#function>
<#-- 生成if不为空条件内容 -->
<#function ifNotEmptyCondition field>
    <#return "${ifNotEmptyConditionWithAlias(field.jfieldName,field)}" >
</#function>
<#-- 将mysql中的关键字加``包裹 -->
<#function wrapMysqlKeyword fieldName>
    <#return "${MetadataUtil.wrapMysqlKeyword(fieldName)}" >
</#function>
