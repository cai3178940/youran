<#-- 功能简介：校验是否需要生成rest服务类 -->
<#include "/abstracted/common.ftl">
<#function getGenRest entity>
    <#local genRest = isTrue(entity.entityFeature.save)
                        ||  isTrue(entity.entityFeature.update)
                        ||  isTrue(entity.entityFeature.delete)
                        ||  isTrue(entity.entityFeature.deleteBatch)
                        ||  isTrue(entity.entityFeature.list)
                        ||  isTrue(entity.entityFeature.show) >
    <#if !genRest>
        <#list entity.holds! as otherEntity,mtm>
            <#assign entityFeature=mtm.getEntityFeature(entity.entityId)>
            <#if isTrue(entityFeature.addRemove)
                || isTrue(entityFeature.set)
                || isTrue(entityFeature.withinEntity) >
                <#local genRest = true >
            </#if>
        </#list>
    </#if>
    <#return genRest>
</#function>
