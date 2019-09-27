<#-- 功能简介：多对多级联扩展字段详情专用 -->
<#include "/common.ftl">
<#--将所有需要详情展示的级联实体放入数组中-->
<#assign mtmCascadeEntitiesForShow = []>
<#--将所有需要详情展示的多对多放入数组中-->
<#assign mtmForShow = []>
<#--将所有级联扩展详情字段按级联实体分组放入数组中-->
<#assign groupMtmCascadeExtsForShow = []>
<#--组装多对多级联扩展相关的数据结构-->
<#list this.holds as otherEntity,mtm>
    <#--初始化当前级联实体对应的级联扩展详情字段-->
    <#assign mtmCascadeExts=[]>
    <#list mtm.getCascadeExtList(this.entityId) as mtmCascadeExt>
        <#assign cascadeField=mtmCascadeExt.cascadeField>
        <#--判断是否开启级联详情展示开关-->
        <#if isTrue(mtmCascadeExt.show)>
            <#assign mtmCascadeExts +=[mtmCascadeExt]>
        </#if>
    </#list>
    <#if mtmCascadeExts?hasContent>
        <#assign groupMtmCascadeExtsForShow += [mtmCascadeExts]>
        <#assign mtmCascadeEntitiesForShow += [otherEntity]>
        <#assign mtmForShow += [mtm]>
    </#if>
</#list>
