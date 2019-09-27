<#-- 功能简介：【对方】多对多级联扩展持有当前实体的【详情】信息 -->
<#include "/common.ftl">
<#--将所有需要当前实体【详情】展示的【对方】实体放入数组中-->
<#assign mtmCascadeEntitiesForOppShow = []>
<#--将所有需要当前实体【详情】展示的多对多放入数组中-->
<#assign mtmForOppShow = []>
<#--将当前实体所有级联扩展【详情】字段按【对方】实体分组放入数组中-->
<#assign groupMtmCascadeExtsForOppShow = []>
<#--定义宏：组装多对多级联扩展相关的数据结构-->
<#macro buildMtmCascadeForOppShow holds>
    <#list holds as otherEntity,mtm>
        <#--初始化当前级联实体对应的级联扩展【详情】字段-->
        <#assign mtmCascadeExts=[]>
        <#list mtm.getCascadeExtList(otherEntity.entityId) as mtmCascadeExt>
            <#assign cascadeField=mtmCascadeExt.cascadeField>
            <#--判断是否开启级联【详情】展示开关-->
            <#if isTrue(mtmCascadeExt.show)>
                <#assign mtmCascadeExts +=[mtmCascadeExt]>
            </#if>
        </#list>
        <#if mtmCascadeExts?hasContent>
            <#assign groupMtmCascadeExtsForOppShow += [mtmCascadeExts]>
            <#assign mtmCascadeEntitiesForOppShow += [otherEntity]>
            <#assign mtmForOppShow += [mtm]>
        </#if>
    </#list>
</#macro>
<@buildMtmCascadeForOppShow this.holds/>
<@buildMtmCascadeForOppShow this.unHolds/>
