<#-- 功能简介：多对多级联扩展字段查询专用 -->
<#include "/common.ftl">
<#--将所有级联扩展查询字段放入数组中-->
<#assign mtmCascadeExtsForQuery = []>
<#--将所有需要查询的级联实体放入数组中-->
<#assign mtmCascadeEntitiesForQuery = []>
<#--将所有需要查询的多对多放入数组中-->
<#assign mtmForQuery = []>
<#--将所有级联扩展查询字段按级联实体分组放入数组中-->
<#assign groupMtmCascadeExtsForQuery = []>
<#--定义宏：组装多对多级联扩展相关的数据结构-->
<#macro buildMtmCascade holds hostEntityId>
    <#list holds as otherEntity,mtm>
        <#--初始化当前级联实体对应的级联扩展查询字段-->
        <#assign mtmCascadeExts=[]>
        <#list mtm.getCascadeExtList(hostEntityId) as mtmCascadeExt>
            <#assign cascadeField=mtmCascadeExt.cascadeField>
            <#--开启级联查询开关 && (是主键 || 字段支持查询) -->
            <#if isTrue(mtmCascadeExt.query) && (isTrue(cascadeField.primaryKey) || isTrue(cascadeField.query))>
                <#assign mtmCascadeExts +=[mtmCascadeExt]>
                <#assign mtmCascadeExtsForQuery += [mtmCascadeExt]>
            </#if>
        </#list>
        <#if mtmCascadeExts?hasContent>
            <#assign groupMtmCascadeExtsForQuery += [mtmCascadeExts]>
            <#assign mtmCascadeEntitiesForQuery += [otherEntity]>
            <#assign mtmForQuery += [mtm]>
        </#if>
    </#list>
</#macro>
<@buildMtmCascade this.holds, this.entityId/>
<@buildMtmCascade this.unHolds, this.entityId/>
