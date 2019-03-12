<#include "/common.ftl">
# ${this.projectDesc}
## 表结构
<#list this.metaEntities as metaEntity>

### ${metaEntity.desc?replace('\'','"')?replace('\n','\\n')}【${metaEntity.tableName}】
|字段名 | 类型 | 非空 | 键 | 注释 |
|------ | ---- | --- | --- | ---- |
    <#list metaEntity.fields as field>
        <#assign pk_display><#if isTrue(field.primaryKey)>主键</#if></#assign>
        <#assign notNull_display><#if isTrue(field.notNull)>是<#elseif field.defaultValue=='NULL'> 否 </#if></#assign>
| ${field.fieldName} | ${field.fieldType}${MetadataUtil.getLengthDisplay(field)} | ${notNull_display} | ${pk_display} | ${MetadataUtil.convertCommentDisplay(field.fieldComment)} |
    </#list>
</#list>
<#if this.mtms??>
    <#list this.mtms as manyTomany>

        <#assign field1=manyTomany.refer1.pkField>
        <#assign field2=manyTomany.refer2.pkField>
        <#assign pkId1=MetadataUtil.getPkAlias(manyTomany.refer1.className,true)>
        <#assign pkId2=MetadataUtil.getPkAlias(manyTomany.refer2.className,true)>
### ${manyTomany.desc?replace('\'','"')?replace('\n','\\n')}【${manyTomany.tableName}】
|字段名 | 类型 | 非空 | 键 | 注释 |
|------ | ---- | --- | --- | ---- |
| ${pkId1} | ${field1.fieldType}${MetadataUtil.getLengthDisplay(field1)} | 是 |  | ${MetadataUtil.convertCommentDisplay(field1.fieldComment)} |
| ${pkId2} | ${field2.fieldType}${MetadataUtil.getLengthDisplay(field2)} | 是 |  | ${MetadataUtil.convertCommentDisplay(field2.fieldComment)} |
    </#list>
</#if>
