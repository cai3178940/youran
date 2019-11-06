<#-- 功能简介：【对方】多对多级联扩展持有当前实体的【列表】信息 -->
<#include "/abstracted/common.ftl">
<#--将所有需要当前实体【列表】展示的【对方】实体放入数组中-->
<#assign mtmCascadeEntitiesForOppList = []>
<#--将所有需要当前实体【列表】展示的多对多放入数组中-->
<#assign mtmForOppList = []>
<#--将当前实体所有级联扩展【列表】字段按【对方】实体分组放入数组中-->
<#assign groupMtmCascadeExtsForOppList = []>
<#--定义宏：组装多对多级联扩展相关的数据结构-->
<#macro buildMtmCascadeForOppList holds>
    <#list holds as otherEntity,mtm>
        <#--初始化当前级联实体对应的级联扩展【列表】字段-->
        <#assign mtmCascadeExts=[]>
        <#list mtm.getCascadeExtList(otherEntity.entityId) as mtmCascadeExt>
            <#assign cascadeField=mtmCascadeExt.cascadeField>
            <#--判断是否开启级联【列表】展示开关-->
            <#if isTrue(mtmCascadeExt.list)>
                <#assign mtmCascadeExts +=[mtmCascadeExt]>
            </#if>
        </#list>
        <#if mtmCascadeExts?hasContent>
            <#assign groupMtmCascadeExtsForOppList += [mtmCascadeExts]>
            <#assign mtmCascadeEntitiesForOppList += [otherEntity]>
            <#assign mtmForOppList += [mtm]>
        </#if>
    </#list>
</#macro>
<@buildMtmCascadeForOppList this.holds/>
<@buildMtmCascadeForOppList this.unHolds/>
