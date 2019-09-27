<#-- 功能简介：多对多级联扩展字段列表专用 -->
<#include "/common.ftl">
<#--将所有需要列表展示的级联实体放入数组中-->
<#assign mtmCascadeEntitiesForList = []>
<#--将所有需要列表展示的多对多放入数组中-->
<#assign mtmForList = []>
<#--将所有级联扩展列表字段按级联实体分组放入数组中-->
<#assign groupMtmCascadeExtsForList = []>
<#--组装多对多级联扩展相关的数据结构-->
<#list this.holds as otherEntity,mtm>
    <#--初始化当前级联实体对应的级联扩展列表字段-->
    <#assign mtmCascadeExts=[]>
    <#list mtm.getCascadeExtList(this.entityId) as mtmCascadeExt>
        <#assign cascadeField=mtmCascadeExt.cascadeField>
        <#--判断是否开启级联列表展示开关-->
        <#if isTrue(mtmCascadeExt.list)>
            <#assign mtmCascadeExts +=[mtmCascadeExt]>
        </#if>
    </#list>
    <#if mtmCascadeExts?hasContent>
        <#assign groupMtmCascadeExtsForList += [mtmCascadeExts]>
        <#assign mtmCascadeEntitiesForList += [otherEntity]>
        <#assign mtmForList += [mtm]>
    </#if>
</#list>
