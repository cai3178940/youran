<#include "/common.ftl">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${this.packageName}.dao.${this.classNameUpper}DAO">

    <#assign wrapTableName=MetadataUtil.wrapMysqlKeyword(this.tableName)>
    <#assign wrapPkFieldName=MetadataUtil.wrapMysqlKeyword(this.pk.fieldName)>
    <#if this.delField??>
        <#assign wrapDelFieldName=MetadataUtil.wrapMysqlKeyword(this.delField.fieldName)>
    </#if>

    <sql id="${this.className}Columns">
        <#list this.fields as field>
        ${r'$'}{alias}.${MetadataUtil.wrapMysqlKeyword(field.fieldName)}<#if field.fieldName?capitalize!=field.jfieldName?capitalize> as ${MetadataUtil.wrapMysqlKeyword(field.jfieldName)}</#if><#if field_has_next>,</#if>
        </#list>
    </sql>


    <select id="findById" resultType="${this.classNameUpper}PO">
        select
            <include refid="${this.className}Columns"><property name="alias" value="t"/></include>
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

    <insert id="_save" <#if isTrue(this.pk.autoIncrement)>useGeneratedKeys="true" </#if>keyProperty="${this.id}" parameterType="${this.classNameUpper}PO">
        insert into ${wrapTableName}(
    <#list this.fields as field>
        ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}<#if field_has_next>,</#if>
    </#list>
        ) VALUES (
    <#list this.fields as field>
        ${r'#'}{${field.jfieldName},jdbcType=${JFieldType.mapperJdbcType(field.jfieldType)}}<#if field_has_next>,</#if>
    </#list>
        )
    </insert>


    <update id="_update" parameterType="${this.classNameUpper}PO">
        update ${wrapTableName} set
        <#assign set_field_arr=[]>
        <#--过滤出需要设值的字段-->
        <#list this.fields as field>
            <#--去除主键、创建人、创建时间-->
            <#if isFalse(field.primaryKey) && !MetaSpecialField.isCreatedBy(field.specialField)  && !MetaSpecialField.isCreatedTime(field.specialField) >
                <#assign set_field_arr = set_field_arr + [ field ] >
            </#if>
        </#list>
        <#list set_field_arr as field>
            <#if field.specialField?? && field.specialField==MetaSpecialField.VERSION>
            ${MetadataUtil.wrapMysqlKeyword(field.fieldName)} = ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}+1<#if field_has_next>,</#if>
            <#elseIf isFalse(field.primaryKey) && !MetaSpecialField.isCreatedBy(field.specialField)  && !MetaSpecialField.isCreatedTime(field.specialField) >
            ${MetadataUtil.wrapMysqlKeyword(field.fieldName)}=${r'#'}{${field.jfieldName},jdbcType=${JFieldType.mapperJdbcType(field.jfieldType)}}<#if field_has_next>,</#if>
            </#if>
        </#list>
        where ${wrapPkFieldName}=${r'#'}{${this.id},jdbcType=${JFieldType.mapperJdbcType(this.pk.jfieldType)}}
        <#if this.versionField??>
        and ${MetadataUtil.wrapMysqlKeyword(this.versionField.fieldName)}=${r'#'}{${this.versionField.jfieldName},jdbcType=${JFieldType.mapperJdbcType(this.versionField.jfieldType)}}
        </#if>
        <#if this.delField??>
        and ${wrapDelFieldName}=0
        </#if>
    </update>

    <delete id="delete">
    <#if this.delField??>
        update ${wrapTableName} set ${wrapDelFieldName}=1
        where ${wrapPkFieldName}=${r'#'}{arg0,jdbcType=${JFieldType.mapperJdbcType(this.pk.jfieldType)}}
        and ${wrapDelFieldName}=0
    <#else>
        delete from ${wrapTableName}
        where ${wrapPkFieldName}=${r'#'}{arg0,jdbcType=${JFieldType.mapperJdbcType(this.pk.jfieldType)}}
    </#if>
    </delete>


    <sql id="queryCondition">
    <#list this.queryFields as field>
        <#if field.queryType==QueryType.BETWEEN>
        <#--between类型查询-->
        <if test="${field.jfieldName}Start != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName}Start !=''</#if> ">
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} >= ${r'#'}{${field.jfieldName}Start}
        </if>
        <if test="${field.jfieldName}End != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName}End !=''</#if> ">
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} &lt;= ${r'#'}{${field.jfieldName}End}
        </if>
        <#elseIf field.queryType==QueryType.IN>
        <#--in类型查询-->
        <if test="${field.jfieldName} != null and ${field.jfieldName}.size() >0 ">
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} in
            <foreach collection="${field.jfieldName}" item="_value" open="(" separator="," close=")">
            ${r'#'}{_value}
            </foreach>
        </if>
        <#elseIf field.queryType==QueryType.LIKE>
        <#--like类型查询-->
        <if test="${field.jfieldName} != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName} !=''</#if> ">
            <bind name="${field.jfieldName}_pattern" value="'%' + ${field.jfieldName} + '%'" />
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} like ${r'#'}{${field.jfieldName}_pattern}
        </if>
        <#else>
        <#--其他类型查询-->
        <if test="${field.jfieldName} != null <#if field.jfieldType==JFieldType.STRING.getJavaType()> and ${field.jfieldName} !=''</#if> ">
            and t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} ${QueryType.mapperSymbol(field.queryType)} ${r'#'}{${field.jfieldName}}
        </if>
        </#if>
    </#list>
    <#assign cascadeIndex=0>
    <#list this.fields as field>
        <#if field.cascadeQueryExts?? && field.cascadeQueryExts?size &gt; 0>
            <#assign cascadeIndex=cascadeIndex+1>
            <#assign con_ex_arr=[]>
            <#list field.cascadeQueryExts as cascadeExt>
                <#assign cascadeField=cascadeExt.cascadeField>
                <#--非between类型-->
                <#if cascadeField.queryType!=QueryType.BETWEEN>
                    <#assign con_ex="${MetadataUtil.camelCaseToSnakeCase(cascadeExt.alias,false)}_con_ex">
                    <#assign con_ex_arr = con_ex_arr + [ con_ex ] >
        <bind name="${con_ex}" value="${cascadeExt.alias} != null <#if cascadeField.jfieldType==JFieldType.STRING.getJavaType()> and ${cascadeExt.alias} !=''</#if>" />
                <#else>
                <#--between类型-->
                    <#assign con_start_ex="${MetadataUtil.camelCaseToSnakeCase(cascadeExt.alias,false)}_start_con_ex">
                    <#assign con_end_ex="${MetadataUtil.camelCaseToSnakeCase(cascadeExt.alias,false)}_end_con_ex">
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
                    <#assign con_ex="${MetadataUtil.camelCaseToSnakeCase(cascadeExt.alias,false)}_con_ex">
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
                    <#assign con_start_ex="${MetadataUtil.camelCaseToSnakeCase(cascadeExt.alias,false)}_start_con_ex">
                    <#assign con_end_ex="${MetadataUtil.camelCaseToSnakeCase(cascadeExt.alias,false)}_end_con_ex">
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
        <#list this.listSortFields as field>
        <if test="${field.jfieldName}SortSign != null and ${field.jfieldName}SortSign!=0">
            t.${MetadataUtil.wrapMysqlKeyword(field.fieldName)} <if test="${field.jfieldName}SortSign > 0">asc</if><if test="${field.jfieldName}SortSign &lt; 0">desc</if>,
        </if>
        </#list>
        <#--默认按【操作日期/创建日期/主键】降序排序-->
        <#if this.operatedTimeField??>
            t.${MetadataUtil.wrapMysqlKeyword(this.operatedTimeField.fieldName)} desc
        <#elseIf this.createdTimeField??>
            t.${MetadataUtil.wrapMysqlKeyword(this.createdTimeField.fieldName)} desc
        <#else>
            t.${wrapPkFieldName} desc
        </#if>
    </sql>

    <select id="findCountByQuery" parameterType="${this.classNameUpper}QO" resultType="int">
        select count(1) from ${wrapTableName} t
        <where>
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        <include refid="queryCondition"/>
        </where>
    </select>

    <select id="findListByQuery" parameterType="${this.classNameUpper}QO" resultType="${this.classNameUpper}ListVO">
        select
            <include refid="${this.className}Columns"><property name="alias" value="t"/></include>
        <#assign cascadeIndex=0>
        <#list this.fields as field>
            <#if field.cascadeListExts?? && field.cascadeListExts?size &gt; 0>
                <#assign cascadeIndex=cascadeIndex+1>
                <#list field.cascadeListExts as cascadeExt>
            ,c${cascadeIndex}.${MetadataUtil.wrapMysqlKeyword(cascadeExt.cascadeField.fieldName)} as ${cascadeExt.alias}
                </#list>
            </#if>
        </#list>
        from ${wrapTableName} t
        <#assign cascadeIndex=0>
        <#list this.fields as field>
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
    <#if isTrue(this.pageSign)>
        limit ${r'#'}{startIndex},${r'#'}{pageSize}
    </#if>
    </select>

<#list this.fields as field>
    <#if isTrue(field.foreignKey)>
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
    <#list this.metaEntity.holds! as otherEntity,mtm>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherType=otherPk.jfieldType>
        <#assign otherFkId=MetadataUtil.getMtmFkAlias(mtm,otherEntity,false)>
        <#assign theFkId=MetadataUtil.getMtmFkAlias(mtm,this.metaEntity,false)>
        <#assign other_fk_id=MetadataUtil.getMtmFkAlias(mtm,otherEntity,true)>
        <#assign the_fk_id=MetadataUtil.getMtmFkAlias(mtm,this.metaEntity,true)>
        <#assign wrapMtmTableName=MetadataUtil.wrapMysqlKeyword(mtm.tableName)>
    <select id="getCountBy${otherCName}" parameterType="${otherType}" resultType="int">
        select count(1)
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_fk_id}
        where
            r.${other_fk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
    </select>

    <select id="findBy${otherCName}" parameterType="${otherType}" resultType="${this.classNameUpper}PO">
        select
            <include refid="${this.className}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_fk_id}
        where
            r.${other_fk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by
        <#if mtm.needId>
            r.id
        <#else>
            r.created_time
        </#if>
    </select>

    <select id="findVOBy${otherCName}" parameterType="${otherType}" resultType="${this.classNameUpper}ListVO">
        select
            <include refid="${this.className}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${pk.fieldName}=r.${the_fk_id}
        where
            r.${other_fk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by
        <#if mtm.needId>
            r.id
        <#else>
            r.created_time
        </#if>
    </select>

    <insert id="add${otherCName}" parameterType="map">
        insert into ${mtm.tableName}(
            ${the_fk_id},
            ${other_fk_id},
            created_time
        )values(
            ${r'#'}{${theFkId},jdbcType=${JFieldType.mapperJdbcType(this.pk.jfieldType)}},
            ${r'#'}{${otherFkId},jdbcType=${JFieldType.mapperJdbcType(otherType)}},
            now()
        )
    </insert>

    <delete id="remove${otherCName}" parameterType="map">
        delete from ${mtm.tableName}
        where ${the_fk_id}=${r'#'}{${theFkId},jdbcType=${JFieldType.mapperJdbcType(this.pk.jfieldType)}} and ${other_fk_id} in
        <foreach collection="${otherFkId}" item="_id" open="(" separator="," close=")">
            ${r'#'}{_id}
        </foreach>
    </delete>

    <delete id="removeAll${otherCName}">
        delete from ${wrapMtmTableName}
        where ${the_fk_id}=${r'#'}{arg0}
    </delete>

    </#list>
    <#list this.metaEntity.unHolds! as otherEntity,mtm>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherType=otherPk.jfieldType>
        <#assign other_fk_id=MetadataUtil.getMtmFkAlias(mtm,otherEntity,true)>
        <#assign the_fk_id=MetadataUtil.getMtmFkAlias(mtm,this.metaEntity,true)>
        <#assign wrapMtmTableName=MetadataUtil.wrapMysqlKeyword(mtm.tableName)>

    <select id="findBy${otherCName}" parameterType="${otherType}" resultType="${this.classNameUpper}PO">
        select
            <include refid="${this.className}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${this.pk.fieldName}=r.${the_fk_id}
        where
            r.${other_fk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by
        <#if mtm.needId>
            r.id
        <#else>
            r.created_time
        </#if>
    </select>

    <select id="findVOBy${otherCName}" parameterType="${otherType}" resultType="${this.classNameUpper}ListVO">
        select
            <include refid="${this.className}Columns"><property name="alias" value="t"/></include>
        from ${wrapTableName} t
        inner join ${wrapMtmTableName} r
            on t.${this.pk.fieldName}=r.${the_fk_id}
        where
            r.${other_fk_id}=${r'#'}{arg0}
        <#if delField??>
            and t.${wrapDelFieldName}=0
        </#if>
        order by
        <#if mtm.needId>
            r.id
        <#else>
            r.created_time
        </#if>
    </select>

    </#list>
<#list this.metaEntity.checkUniqueIndexes as index>
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
            <if test="${this.id} != null  ">
                and t.${this.pk.fieldName} != ${r'#'}{${this.id}}
            </if>
        </where>
    </select>

</#list>

    <!-- 以上是自动生成的代码，尽量不要手动修改，新的sql请写在本行注释以下区域 -->


</mapper>
