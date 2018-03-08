<#include "/common.ftl">
## 表结构
<#list metaEntities as metaEntity>

### ${metaEntity.desc?replace('\'','"')?replace('\n','\\n')}【${metaEntity.tableName}】
|字段名 | 类型 | 非空 | 键 | 注释 |
|------ | ---- | --- | -- | ---- |
    <#list metaEntity.fields as field>
        <#assign length_holder><#if MetadataUtil.showFieldLength(field.fieldType)>(${field.fieldLength}<#if MetadataUtil.showFieldScale(field.fieldType)>,${field.fieldScale}</#if>)</#if></#assign>
        <#assign pk_holder><#if field.primaryKey==1>主键</#if></#assign>
        <#assign notNull_holder><#if field.notNull==1>是<#elseif field.defaultValue=='NULL'> 否 </#if></#assign>
        <#assign comment_holder><#if field.fieldComment??>${field.fieldComment?replace('\'','"')}</#if></#assign>
| ${field.fieldName} | ${field.fieldType}${length_holder} | ${notNull_holder} | ${pk_holder} | ${comment_holder} |
    </#list>
</#list>
<#if mtms??>
    <#list mtms as manyTomany>

        <#assign field1=manyTomany.refer1.pkField>
        <#assign field2=manyTomany.refer2.pkField>
        <#assign length_holder1><#if MetadataUtil.showFieldLength(field1.fieldType)>(${field1.fieldLength}<#if MetadataUtil.showFieldScale(field1.fieldType)>,${field1.fieldScale}</#if>)</#if></#assign>
        <#assign length_holder2><#if MetadataUtil.showFieldLength(field2.fieldType)>(${field2.fieldLength}<#if MetadataUtil.showFieldScale(field2.fieldType)>,${field2.fieldScale}</#if>)</#if></#assign>
        <#assign comment_holder1><#if field1.fieldComment??>${field1.fieldComment?replace('\'','"')}</#if></#assign>
        <#assign comment_holder2><#if field2.fieldComment??>${field2.fieldComment?replace('\'','"')}</#if></#assign>
        <#assign pkId1=MetadataUtil.getPkAlias(manyTomany.refer1.className,true)>
        <#assign pkId2=MetadataUtil.getPkAlias(manyTomany.refer2.className,true)>
### ${manyTomany.desc?replace('\'','"')?replace('\n','\\n')}【${manyTomany.tableName}】
|字段名 | 类型 | 非空 | 键 | 注释 |
|------ | ---- | --- | -- | ---- |
| ${pkId1} | ${field1.fieldType}${length_holder} | 是 |  | ${comment_holder1} |
| ${pkId2} | ${field2.fieldType}${length_holder} | 是 |  | ${comment_holder2} |
    </#list>
</#if>
