<#-- 多对多级联扩展字段查询专用 -->
<#include "/common.ftl">
<#--将所有级联扩展查询字段放入数组中-->
<#assign mtmCascadeExtsForQuery = []>
<#--将所有需要查询的级联实体放入数组中-->
<#assign mtmCascadeEntitiesForQuery = []>
<#--将所有需要查询的多对多放入数组中-->
<#assign mtmForQuery = []>
<#--将所有级联扩展查询字段按级联实体分组放入数组中-->
<#assign groupMtmCascadeExtsForQuery = []>
<#list this.holds as cascadeEntity,mtm>
    <#--初始化当前级联实体对应的级联扩展查询字段-->
    <#assign mtmCascadeExts=[]>
    <#list mtm.getCascadeExtList(this.entityId) as mtmCascadeExt>
        <#assign cascadeField=mtmCascadeExt.cascadeField>
        <#if isTrue(mtmCascadeExt.query) && isTrue(cascadeField.query)>
            <#assign mtmCascadeExts +=[mtmCascadeExt]>
            <#assign mtmCascadeExtsForQuery += [mtmCascadeExt]>
        </#if>
    </#list>
    <#if mtmCascadeExts?hasContent>
        <#assign groupMtmCascadeExtsForQuery += [mtmCascadeExts]>
        <#assign mtmCascadeEntitiesForQuery += [cascadeEntity]>
        <#assign mtmForQuery += [mtm]>
    </#if>
</#list>
<#list this.unHolds as cascadeEntity,mtm>
    <#--初始化当前级联实体对应的级联扩展查询字段-->
    <#assign mtmCascadeExts=[]>
    <#list mtm.getCascadeExtList(this.entityId) as mtmCascadeExt>
        <#assign cascadeField=mtmCascadeExt.cascadeField>
        <#if isTrue(mtmCascadeExt.query) && isTrue(cascadeField.query)>
            <#assign mtmCascadeExts +=[mtmCascadeExt]>
            <#assign mtmCascadeExtsForQuery += [mtmCascadeExt]>
        </#if>
    </#list>
    <#if mtmCascadeExts?hasContent>
        <#assign groupMtmCascadeExtsForQuery += [mtmCascadeExts]>
        <#assign mtmCascadeEntitiesForQuery += [cascadeEntity]>
        <#assign mtmForQuery += [mtm]>
    </#if>
</#list>
