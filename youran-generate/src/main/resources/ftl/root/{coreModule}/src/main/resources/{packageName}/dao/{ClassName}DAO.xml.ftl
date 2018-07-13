<#include "/common.ftl">
<#include "/entity_common.ftl">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.dao.${CName}DAO">

    <#assign wrapTableName=MetadataUtil.wrapMysqlKeyword(tableName)>
    <#assign wrapPkFieldName=MetadataUtil.wrapMysqlKeyword(pk.fieldName)>
    <#if delField??>
        <#assign wrapDelFieldName=MetadataUtil.wrapMysqlKeyword(delField.fieldName)>
    </#if>

    <sql id="${cName}Columns">
        <#list fields as field>
        ${r'$'}{alias}.${MetadataUtil.wrapMysqlKeyword(field.fieldName)}<#if field.fieldName?capitalize!=field.jfieldName?capitalize> as ${MetadataUtil.wrapMysqlKeyword(field.jfieldName)}</#if><#if field_has_next>,</#if>
        </#list>
    </sql>


    <select id="findById" resultType="${CName}PO">
        select
            <include refid="${cName}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        <where>
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
            and t.${wrapPkFieldName} = ${r'#'}{arg0}
        </where>
        limit 1
    </select>

    <select id="exist" resultType="boolean">
        select count(1) from ${wrapTableName}
        <where>
        <#if delField??>
            and ${wrapDelFieldName}=0
        </#if>
            and ${wrapPkFieldName} = ${r'#'}{arg0}
        </where>
    </select>

    <insert id="_save" <#if pk.autoIncrement==1>useGeneratedKeys="true" </#if>keyProperty="${id}" parameterType="${CName}PO">
        insert into ${wrapTableName}(
    <#list fields as field>
        ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}<#if field_has_next>,</#if>
    </#list>
        ) VALUES (
    <#list fields as field>
        ${r'#'}{${field.jfieldName},jdbcType=${JFieldType.mapperJdbcType(field.jfieldType)}}<#if field_has_next>,</#if>
    </#list>
        )
    </insert>


    <update id="_update" parameterType="${CName}PO">
        update ${wrapTableName} set
        <#list fields as field>
            <#if field.specialField?? && field.specialField==MetaSpecialField.VERSION>
            ${MetadataUtil.wrapMysqlKeyword(field.fieldName)} = ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}+1<#if field_has_next>,</#if>
            <#else>
            ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}=${r'#'}{${field.jfieldName},jdbcType=${JFieldType.mapperJdbcType(field.jfieldType)}}<#if field_has_next>,</#if>
            </#if>
        </#list>
        where ${wrapPkFieldName}=${r'#'}{${id},jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}}
        <#if versionField??>
        and ${MetadataUtil.wrapMysqlKeyword(versionField.fieldName)}=${r'#'}{${versionField.jfieldName},jdbcType=${JFieldType.mapperJdbcType(versionField.jfieldType)}}
        </#if>
        <#if delField??>
        and ${wrapDelFieldName}=0
        </#if>
    </update>

    <delete id="delete">
    <#if delField??>
        update ${wrapTableName} set ${wrapDelFieldName}=1
        where ${wrapPkFieldName}=${r'#'}{arg0,jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}}
        and ${wrapDelFieldName}=0
    <#else>
        delete from ${wrapTableName}
        where ${wrapPkFieldName}=${r'#'}{arg0,jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}}
    </#if>
    </delete>


    <sql id="queryCondition">
    <#list queryFields as field>
        <#--非between类型查询-->
        <#if field.queryType!=QueryType.BETWEEN>
        <if test="${field.jfieldName} != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName} !=''</#if> ">
            <#if field.queryType==QueryType.LIKE>
            <bind name="${field.jfieldName}_pattern" value="'%' + ${field.jfieldName} + '%'" />
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} ${QueryType.mapperSymbol(field.queryType)} ${r'#'}{${field.jfieldName}_pattern}
            <#else>
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} ${QueryType.mapperSymbol(field.queryType)} ${r'#'}{${field.jfieldName}}
            </#if>
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
    <#assign cascadeIndex=0>
    <#list fields as field>
        <#if field.cascadeQueryExts?? && field.cascadeQueryExts?size &gt; 0>
            <#assign cascadeIndex=cascadeIndex+1>
            <#assign con_ex_arr=[]>
            <#list field.cascadeQueryExts as cascadeExt>
                <#assign cascadeField=cascadeExt.cascadeField>
                <#--非between类型-->
                <#if cascadeField.queryType!=QueryType.BETWEEN>
                    <#assign con_ex="${MetadataUtil.camelCaseToUnderline(cascadeExt.alias,false)}_con_ex">
                    <#assign con_ex_arr = con_ex_arr + [ con_ex ] >
        <bind name="${con_ex}" value="${cascadeExt.alias} != null <#if cascadeField.jfieldType==JFieldType.STRING.getJavaType()> and ${cascadeExt.alias} !=''</#if>" />
                <#else>
                <#--between类型-->
                    <#assign con_start_ex="${MetadataUtil.camelCaseToUnderline(cascadeExt.alias,false)}_start_con_ex">
                    <#assign con_end_ex="${MetadataUtil.camelCaseToUnderline(cascadeExt.alias,false)}_end_con_ex">
                    <#assign con_ex_arr = con_ex_arr + [ con_start_ex,con_end_ex ] >
        <bind name="${con_start_ex}" value="${cascadeExt.alias}Start != null <#if cascadeField.jfieldType==JFieldType.STRING.getJavaType()> and ${cascadeExt.alias}Start !=''</#if>" />
        <bind name="${con_end_ex}" value="${cascadeExt.alias}End != null <#if cascadeField.jfieldType==JFieldType.STRING.getJavaType()> and ${cascadeExt.alias}End !=''</#if>" />
                </#if>
            </#list>
        <if test="${con_ex_arr?join(' or ')} ">
            and exists(
                select 1 from ${MetadataUtil.wrapMysqlKeyword(field.foreignEntity.tableName)} e${cascadeIndex}
                where e${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(field.foreignEntity.pkField.fieldName)} = t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)}
            <#if field.foreignEntity.delField??>
                and e${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(field.foreignEntity.delField.fieldName)}=0
            </#if>
            <#list field.cascadeQueryExts as cascadeExt>
                <#assign cascadeField=cascadeExt.cascadeField>
                <#--非between类型-->
                <#if cascadeField.queryType!=QueryType.BETWEEN>
                    <#assign con_ex="${MetadataUtil.camelCaseToUnderline(cascadeExt.alias,false)}_con_ex">
            <if test="${con_ex}">
                    <#if cascadeField.queryType==QueryType.LIKE>
                <bind name="${cascadeExt.alias}_pattern" value="'%' + ${cascadeExt.alias} + '%'" />
                and e${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(cascadeField.fieldName)} ${QueryType.mapperSymbol(cascadeField.queryType)} ${r'#'}{${cascadeExt.alias}_pattern}
                    <#else>
                and e${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(cascadeField.fieldName)} ${QueryType.mapperSymbol(cascadeField.queryType)} ${r'#'}{${cascadeExt.alias}}
                    </#if>
            </if>
                <#else>
                <#--between类型-->
                    <#assign con_start_ex="${MetadataUtil.camelCaseToUnderline(cascadeExt.alias,false)}_start_con_ex">
                    <#assign con_end_ex="${MetadataUtil.camelCaseToUnderline(cascadeExt.alias,false)}_end_con_ex">
            <if test="${con_start_ex}">
                and e${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(cascadeField.fieldName)} >= ${r'#'}{${cascadeExt.alias}Start}
            </if>
            <if test="${con_end_ex}">
                and e${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(cascadeField.fieldName)} &lt;= ${r'#'}{${cascadeExt.alias}End}
            </if>
                </#if>
            </#list>
            )
        </if>
        </#if>
    </#list>
    </sql>

    <sql id="orderCondition">
        order by
        <#list listSortFields as field>
        <if test="${field.jfieldName}SortSign != null and ${field.jfieldName}SortSign!=0">
            t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} <if test="${field.jfieldName}SortSign > 0">asc</if><if test="${field.jfieldName}SortSign &lt; 0">desc</if>,
        </if>
        </#list>
        <#--默认按【操作日期/创建日期/主键】降序排序-->
        <#if operateDateField??>
            t.${MetadataUtil.wrapMysqlKeyword(operateDateField.fieldName)} desc
        <#elseIf createDateField??>
            t.${MetadataUtil.wrapMysqlKeyword(createDateField.fieldName)} desc
        <#else>
            t.${wrapPkFieldName} desc
        </#if>
    </sql>

    <select id="findCountByQuery" parameterType="${CName}QO" resultType="int">
        select count(1) from ${wrapTableName} t
        <where>
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        <include refid="queryCondition"/>
        </where>
    </select>

    <select id="findListByQuery" parameterType="${CName}QO" resultType="${CName}ListVO">
        select
            <include refid="${cName}Columns"><property name="alias" value="t"/></include>
        <#assign cascadeIndex=0>
        <#list fields as field>
            <#if field.cascadeListExts?? && field.cascadeListExts?size &gt; 0>
                <#assign cascadeIndex=cascadeIndex+1>
                <#list field.cascadeListExts as cascadeExt>
            ,c${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(cascadeExt.cascadeField.fieldName)} as ${cascadeExt.alias}
                </#list>
            </#if>
        </#list>
        from ${wrapTableName} t
        <#assign cascadeIndex=0>
        <#list fields as field>
            <#if field.cascadeListExts?? && field.cascadeListExts?size &gt; 0>
                <#assign cascadeIndex=cascadeIndex+1>
        left outer join ${MetadataUtil.wrapMysqlKeyword(field.foreignEntity.tableName)} c${cascadeIndex}
            on c${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(field.foreignEntity.pkField.fieldName)} = t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)}
                <#if field.foreignEntity.delField??>
            and c${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(field.foreignEntity.delField.fieldName)}=0
                </#if>
            </#if>
        </#list>
        <where>
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        <include refid="queryCondition"/>
        </where>
        <include refid="orderCondition"/>
    <#if pageSign == 1>
        limit ${r'#'}{startIndex},${r'#'}{pageSize}
    </#if>
    </select>

<#list fields as field>
    <#if field.foreignKey==1>
        <#assign wrapFieldName=MetadataUtil.wrapMysqlKeyword(field.fieldName)>
    <select id="getCountBy${field.jfieldName?capFirst}" parameterType="${field.jfieldType}" resultType="int">
        select count(1)
        from ${wrapTableName} t
        where
            t.${wrapFieldName}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
    </select>

    </#if>
</#list>
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as entity>
        <#assign mtm=metaEntity.holdMtms[entity_index]/>
        <#assign otherCName=entity.className?capFirst>
        <#assign othercName=entity.className?uncapFirst>
        <#assign otherPk=entity.pkField>
        <#assign otherType=otherPk.jfieldType>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
        <#assign thePkId=MetadataUtil.getPkAlias(cName,false)>
        <#assign other_pk_id=MetadataUtil.getPkAlias(othercName,true)>
        <#assign the_pk_id=MetadataUtil.getPkAlias(cName,true)>
        <#assign wrapMtmTableName=MetadataUtil.wrapMysqlKeyword(mtm.tableName)>
    <select id="getCountBy${otherCName}" parameterType="${otherType}" resultType="int">
        select count(1)
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_pk_id}
        where
            r.${other_pk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
    </select>

    <select id="findBy${otherCName}" parameterType="${otherType}" resultType="${CName}PO">
        select
            <include refid="${cName}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_pk_id}
        where
            r.${other_pk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by r.create_date
    </select>

    <select id="findVOBy${otherCName}" parameterType="${otherType}" resultType="${CName}ListVO">
        select
            <include refid="${cName}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_pk_id}
        where
            r.${other_pk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by r.create_date
    </select>

    <insert id="add${otherCName}" parameterType="map">
        insert into ${mtm.tableName}(${the_pk_id},${other_pk_id},create_date)
        values(${r'#'}{${thePkId},jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}},${r'#'}{${otherPkId},jdbcType=${JFieldType.mapperJdbcType(otherType)}},now())
    </insert>

    <delete id="remove${otherCName}" parameterType="map">
        delete from ${mtm.tableName}
        where ${the_pk_id}=${r'#'}{${thePkId},jdbcType=${JFieldType.mapperJdbcType(pk.jfieldType)}} and ${other_pk_id} in
        <foreach collection="${otherPkId}" item="_id" open="(" separator="," close=")">
            ${r'#'}{_id}
        </foreach>
    </delete>

    <delete id="removeAll${otherCName}">
        delete from ${wrapMtmTableName}
        where ${the_pk_id}=${r'#'}{arg0}
    </delete>

    </#list>
</#if>
<#if metaEntity.mtmUnHoldRefers??>
    <#list metaEntity.mtmUnHoldRefers as entity>
        <#assign mtm=metaEntity.unHoldMtms[entity_index]/>
        <#assign otherCName=entity.className?capFirst>
        <#assign othercName=entity.className?uncapFirst>
        <#assign otherPk=entity.pkField>
        <#assign otherType=otherPk.jfieldType>
        <#assign other_pk_id=MetadataUtil.getPkAlias(othercName,true)>
        <#assign the_pk_id=MetadataUtil.getPkAlias(cName,true)>
        <#assign wrapMtmTableName=MetadataUtil.wrapMysqlKeyword(mtm.tableName)>

    <select id="findBy${otherCName}" parameterType="${otherType}" resultType="${CName}PO">
        select
            <include refid="${cName}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_pk_id}
        where
            r.${other_pk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by r.create_date
    </select>

    <select id="findVOBy${otherCName}" parameterType="${otherType}" resultType="${CName}ListVO">
        select
            <include refid="${cName}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_pk_id}
        where
            r.${other_pk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by r.create_date
    </select>

    </#list>
</#if>
<#list metaEntity.checkUniqueIndexes as index>
    <#assign suffix=(index_index==0)?string('',''+index_index)>
    <select id="notUnique${suffix}" resultType="boolean">
        select count(1) from ${wrapTableName} t
        <where>
            <#if delField??>
                and t.${wrapDelFieldName}=0
            </#if>
            <#list index.fields as field>
                and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} = ${r'#'}{${field.jfieldName}}
            </#list>
            <if test="${id} != null  ">
                and t.${pk.fieldName} != ${r'#'}{${id}}
            </if>
        </where>
    </select>

</#list>
</mapper>
