<#assign cName=metaEntity.className?uncapFirst><#--类名小写-->
<#assign CName=metaEntity.className><#--类名大写-->
<#assign className=metaEntity.className?uncapFirst><#--类名小写-->
<#assign ClassName=metaEntity.className><#--类名大写-->
<#assign tableName=metaEntity.tableName><#--表名-->
<#assign title=metaEntity.title><#--标题-->
<#assign desc=metaEntity.desc><#--描述-->
<#assign pk=metaEntity.pkField><#--主键字段-->
<#assign id=metaEntity.pkField.jfieldName><#--主键字段名称-->
<#assign Id=metaEntity.pkField.jfieldName?capFirst><#--主键字段名称大写-->
<#assign type=metaEntity.pkField.jfieldType><#--主键字段类型-->
<#assign fields=metaEntity.fields><#--字段列表-->
<#assign versionField=metaEntity.versionField><#--版本字段-->
<#assign delField=metaEntity.delField><#--逻辑删除字段-->
<#assign queryFields=metaEntity.queryFields><#--查询字段-->

<#-- 定义类名截取函数 -->
<#function fetchClassName dicType>
    <#local index=dicType?lastIndexOf(".")/>
    <#if index gt 0>
        <#return dicType?substring(index+1)>
    <#else>
        <#return dicType>
    </#if>
</#function>

<#-- 定义包名截取函数 -->
<#function fetchPackageName dicType>
    <#local index=dicType?lastIndexOf(".")/>
    <#if index gt 0>
        <#return dicType?substring(0,index)>
    </#if>
</#function>
