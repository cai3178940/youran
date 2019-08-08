<#include "/common.ftl">
<#list this.metaEntities as metaEntity>
DROP TABLE IF EXISTS `${metaEntity.tableName}`;

CREATE TABLE `${metaEntity.tableName}` (
    <#list metaEntity.fields as field>
        <#assign comma_holder><#if field_has_next || metaEntity.pkField?? || (metaEntity.indexes?? && (metaEntity.indexes?size > 0))>,</#if></#assign>
    `${field.fieldName}` ${field.fieldType}${MetadataUtil.getLengthDisplay(field)}${MetadataUtil.getAutoIncrementDisplay(field)}${MetadataUtil.getNotNullDisplay(field)}${MetadataUtil.getDefaultDisplay(field)}${MetadataUtil.getCommentDisplay(field.fieldComment,true)}${comma_holder}
    </#list>
    <#if metaEntity.pkField??>
    PRIMARY KEY (`${metaEntity.pkField.fieldName}`)<#if metaEntity.indexes?? && (metaEntity.indexes?size > 0)>,</#if>
    </#if>
    <#if metaEntity.indexes??>
        <#list metaEntity.indexes as index>
    <#if isTrue(index.unique)>UNIQUE </#if>KEY `${index.indexName}` (<#list index.fields as field>`${field.fieldName}`<#if field_has_next >,</#if></#list>) USING BTREE<#if index_has_next>,</#if>
        </#list>
    </#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8${MetadataUtil.getCommentDisplay(metaEntity.desc,false)};

</#list>
<#if this.mtms??>
    <#list this.mtms as manyTomany>
        <#assign field1=manyTomany.refer1.pkField>
        <#assign field2=manyTomany.refer2.pkField>
        <#assign fkId1=MetadataUtil.getMtmFkAlias(manyTomany,manyTomany.refer1,true)>
        <#assign fkId2=MetadataUtil.getMtmFkAlias(manyTomany,manyTomany.refer2,true)>
DROP TABLE IF EXISTS `${manyTomany.tableName}`;

CREATE TABLE `${manyTomany.tableName}` (
    `${fkId1}` ${field1.fieldType}${MetadataUtil.getLengthDisplay(field1)} NOT NULL${MetadataUtil.getCommentDisplay(field1.fieldComment,true)},
    `${fkId2}` ${field2.fieldType}${MetadataUtil.getLengthDisplay(field2)} NOT NULL${MetadataUtil.getCommentDisplay(field2.fieldComment,true)},
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    KEY `i_${manyTomany.tableName}_1` (`${fkId1}`),
    KEY `i_${manyTomany.tableName}_2` (`${fkId2}`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${manyTomany.desc?replace('\'','"')?replace('\n','\\n')}';

    </#list>
</#if>
