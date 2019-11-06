<#-- 功能简介：获取当前实体被对方所持有的多对多 -->
<#include "/abstracted/common.ftl">
<#--将所有持有当前实体的【对方】实体放入数组中-->
<#assign mtmEntitiesForOpp = []>
<#--将所有持有当前实体的【多对多】放入数组中-->
<#assign mtmsForOpp = []>
<#--定义宏：组装多对多级联扩展相关的数据结构-->
<#macro buildMtmsForOpp holds>
    <#list holds as otherEntity,mtm>
        <#if mtm.isHold(otherEntity.entityId)>
            <#assign mtmsForOpp += [mtm]>
            <#assign mtmEntitiesForOpp += [otherEntity]>
        </#if>
    </#list>
</#macro>
<@buildMtmsForOpp this.holds/>
<@buildMtmsForOpp this.unHolds/>
