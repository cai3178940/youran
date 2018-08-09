<#include "/common.ftl">
<#list this.metaEntities as metaEntity>
DROP TABLE IF EXISTS `${metaEntity.tableName}`;

CREATE TABLE `${metaEntity.tableName}` (
    <#list metaEntity.fields as field>
        <#assign length_holder><#if MetadataUtil.showFieldLength(field.fieldType)>(${field.fieldLength}<#if MetadataUtil.showFieldScale(field.fieldType)>,${field.fieldScale}</#if>)</#if></#assign>
        <#assign autoIncrement_holder><#if isTrue(field.autoIncrement)> AUTO_INCREMENT</#if></#assign>
        <#assign notNull_holder><#if isTrue(field.notNull)> NOT NULL<#elseif field.defaultValue=='NULL'> DEFAULT NULL</#if></#assign>
        <#assign default_holder><#if field.defaultValue!='NULL'> DEFAULT ${field.defaultValue}</#if></#assign>
        <#assign comment_holder><#if field.fieldComment??> COMMENT '${field.fieldComment?replace('\'','"')}'</#if></#assign>
        <#assign comma_holder><#if field_has_next || metaEntity.pkField?? || (metaEntity.indexes?? && (metaEntity.indexes?size > 0))>,</#if></#assign>
        <#if isTrue(field.primaryKey)>
    `${field.fieldName}` ${field.fieldType}${length_holder}${autoIncrement_holder}${comment_holder}${comma_holder}
        <#else>
    `${field.fieldName}` ${field.fieldType}${length_holder}${notNull_holder}${default_holder}${comment_holder}${comma_holder}
        </#if>
    </#list>
    <#if metaEntity.pkField??>
    PRIMARY KEY (`${metaEntity.pkField.fieldName}`)<#if metaEntity.indexes?? && (metaEntity.indexes?size > 0)>,</#if>
    </#if>
    <#if metaEntity.indexes??>
        <#list metaEntity.indexes as index>
    <#if isTrue(index.unique)>UNIQUE </#if>KEY `${index.indexName}` (<#list index.fields as field>`${field.fieldName}`<#if field_has_next >,</#if></#list>) USING BTREE<#if index_has_next>,</#if>
        </#list>
    </#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${metaEntity.desc?replace('\'','"')?replace('\n','\\n')}';

</#list>
<#if this.mtms??>
    <#list this.mtms as manyTomany>
        <#assign field1=manyTomany.refer1.pkField>
        <#assign field2=manyTomany.refer2.pkField>
        <#assign length_holder1><#if field1.jfieldType!=JFieldType.DATE.getJavaType() && field1.fieldLength gt 0>(${field1.fieldLength}<#if field1.fieldScale??>,${field1.fieldScale}</#if>)</#if></#assign>
        <#assign length_holder2><#if field2.jfieldType!=JFieldType.DATE.getJavaType() && field2.fieldLength gt 0>(${field2.fieldLength}<#if field2.fieldScale??>,${field2.fieldScale}</#if>)</#if></#assign>
        <#assign comment_holder1><#if field1.fieldComment??> COMMENT '${field1.fieldComment?replace('\'','"')}'</#if></#assign>
        <#assign comment_holder2><#if field2.fieldComment??> COMMENT '${field2.fieldComment?replace('\'','"')}'</#if></#assign>
        <#assign pkId1=MetadataUtil.getPkAlias(manyTomany.refer1.className,true)>
        <#assign pkId2=MetadataUtil.getPkAlias(manyTomany.refer2.className,true)>
DROP TABLE IF EXISTS `${manyTomany.tableName}`;

CREATE TABLE `${manyTomany.tableName}` (
    `${pkId1}` ${field1.fieldType}${length_holder1} NOT NULL ${comment_holder1},
    `${pkId2}` ${field2.fieldType}${length_holder2} NOT NULL ${comment_holder2},
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    KEY `i_${manyTomany.tableName}_1` (`${pkId1}`),
    KEY `i_${manyTomany.tableName}_2` (`${pkId2}`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${manyTomany.desc?replace('\'','"')?replace('\n','\\n')}';

    </#list>
</#if>
