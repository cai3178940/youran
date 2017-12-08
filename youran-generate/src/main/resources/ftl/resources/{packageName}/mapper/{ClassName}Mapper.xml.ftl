<#include "/common.ftl">
<#include "/entity_common.ftl">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${CName}Mapper">
    <select id="findById" resultType="${CName}PO">
        select
        <#list fields as field>
            ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}<#if field.fieldName?capitalize!=field.jfieldName?capitalize> as ${MetadataUtil.wrapMysqlKeyword(field.jfieldName)}</#if><#if field_has_next>,</#if>
        </#list>
        from ${MetadataUtil.wrapMysqlKeyword(tableName)}
        where 1=1
        <#if delField??>
            and ${MetadataUtil.wrapMysqlKeyword(delField.fieldName)}=0
        </#if>
        and ${MetadataUtil.wrapMysqlKeyword(pk.fieldName)} = ${r'#'}{arg0}
    </select>

    <insert id="save" <#if pk.autoIncrement==1>useGeneratedKeys="true" </#if>keyProperty="${id}" parameterType="${CName}PO">
        insert into ${MetadataUtil.wrapMysqlKeyword(tableName)}(
    <#list fields as field>
        ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}<#if field_has_next>,</#if>
    </#list>
        ) VALUES (
    <#list fields as field>
    ${r'#'}{${field.jfieldName},jdbcType=${JFieldType.mapperJdbcType(field.jfieldType)}}<#if field_has_next>,</#if>
    </#list>
        )
    </insert>


    <update id="update" parameterType="${CName}PO">
        update ${MetadataUtil.wrapMysqlKeyword(tableName)} set
        <#list fields as field>
            <#if field.specialField?? && field.specialField==MetaSpecialField.VERSION>
            ${MetadataUtil.wrapMysqlKeyword(field.fieldName)} = ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}+1<#if field_has_next>,</#if>
            <#else>
            ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}=${r'#'}{${field.jfieldName},jdbcType=${JFieldType.mapperJdbcType(field.jfieldType)}}<#if field_has_next>,</#if>
            </#if>
        </#list>
        where ${MetadataUtil.wrapMysqlKeyword(pk.fieldName)}=${r'#'}{${id},jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}}
        <#if versionField??>
        and ${MetadataUtil.wrapMysqlKeyword(versionField.fieldName)}=${r'#'}{${versionField.jfieldName},jdbcType=${JFieldType.mapperJdbcType(versionField.jfieldType)}}
        </#if>
        <#if delField??>
        and ${MetadataUtil.wrapMysqlKeyword(delField.fieldName)}=0
        </#if>
    </update>

    <delete id="delete">
    <#if delField??>
        update ${MetadataUtil.wrapMysqlKeyword(tableName)} set ${MetadataUtil.wrapMysqlKeyword(delField.fieldName)}=1
        where ${MetadataUtil.wrapMysqlKeyword(pk.fieldName)}=${r'#'}{arg0,jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}}
        and ${MetadataUtil.wrapMysqlKeyword(delField.fieldName)}=0
    <#else>
        delete from ${MetadataUtil.wrapMysqlKeyword(tableName)}
        where ${MetadataUtil.wrapMysqlKeyword(pk.fieldName)}=${r'#'}{arg0,jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}}
    </#if>
    </delete>


    <sql id="queryCondition">
    <#list queryFields as field>
        <#--非between类型查询-->
        <#if field.queryType!=QueryType.BETWEEN>
        <if test="${field.jfieldName} != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName} !=''</#if> ">
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} ${QueryType.mapperQueryType(field.queryType)} ${r'#'}{${field.jfieldName}}
        </if>
        <#else>
        <#--between类型查询-->
        <if test="${field.jfieldName}Start != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName}Start !=''</#if> ">
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} >= ${r'#'}{${field.jfieldName}Start}
        </if>
        <if test="${field.jfieldName}End != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName}End !=''</#if> ">
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} &lt;= ${r'#'}{${field.jfieldName}End}
        </if>
        </#if>
    </#list>
    </sql>

    <select id="findCountByQuery" parameterType="${CName}QueryDTO" resultType="int">
        select count(*) from (
        select * from ${MetadataUtil.wrapMysqlKeyword(tableName)} t
        where 1=1
    <#if delField??>
        and t.${MetadataUtil.wrapMysqlKeyword(delField.fieldName)}=0
    </#if>
        <include refid="queryCondition"/>
        ) as count_select_
    </select>

    <select id="findListByQuery" parameterType="${CName}QueryDTO" resultType="${CName}ListVO">
        select * from ${MetadataUtil.wrapMysqlKeyword(tableName)} t
        where 1=1
    <#if delField??>
        and t.${MetadataUtil.wrapMysqlKeyword(delField.fieldName)}=0
    </#if>
        <include refid="queryCondition"/>
        limit ${r'#'}{startIndex},${r'#'}{pageSize}
    </select>

</mapper>
