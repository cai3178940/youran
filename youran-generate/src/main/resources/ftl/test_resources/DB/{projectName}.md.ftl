<#include "/common.ftl">
<#list metaEntities as metaEntity>

### ${metaEntity.desc?replace('\'','"')?replace('\n','\\n')}【${metaEntity.tableName}】
|字段名 | 类型 | 非空 | 键 | 注释 |
|------ | ---- | --- | -- | ---- |
    <#list metaEntity.fields as field>
        <#assign length_holder><#if field.jfieldType!=JFieldType.DATE.getJavaType() && field.fieldLength gt 0>(${field.fieldLength}<#if MetadataUtil.showFieldScale(field.fieldType)>,${field.fieldScale}</#if>)</#if></#assign>
        <#assign pk_holder><#if field.primaryKey==1>主键</#if></#assign>
        <#assign notNull_holder><#if field.notNull==1>是<#elseif field.defaultValue=='NULL'> 否 </#if></#assign>
        <#assign comment_holder><#if field.fieldComment??>${field.fieldComment?replace('\'','"')}</#if></#assign>
| ${field.fieldName} | ${field.fieldType}${length_holder} | ${notNull_holder} | ${pk_holder} | ${comment_holder} |
    </#list>
</#list>
