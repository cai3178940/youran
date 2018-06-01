<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "io.swagger.annotations.ApiModel"/>
<@import "io.swagger.annotations.ApiModelProperty"/>
<#if pageSign == 1>
    <@import "${commonPackage}.pojo.qo.PageQO"/>
<#else>
    <@import "${commonPackage}.pojo.qo.AbstractQO"/>
</#if>
<@importStatic "${packageName}.pojo.example.${CName}Example.*"/>
<@classCom "查询【${title}】的参数"/>
@ApiModel(description = "新增【${title}】的参数")
public class ${CName}QO extends <#if pageSign == 1>PageQO<#else>AbstractQO</#if> {

<#--定义宏-查询字段申明模块-->
<#macro queryField field alias="" examplePackage="">
    <#if alias?hasContent>
        <#assign jfieldName=alias>
    <#else>
        <#assign jfieldName=field.jfieldName>
    </#if>
    @ApiModelProperty(notes = ${examplePackage}N_${field.jfieldName?upperCase},example = ${examplePackage}E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.STRING.getJavaType()>
        <@import "org.hibernate.validator.constraints.Length"/>
    @Length(max = ${field.fieldLength},message = "${field.jfieldName}最大长度不能超过{max}")
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <@import "java.util.Date"/>
        <@import "com.fasterxml.jackson.annotation.JsonFormat"/>
        <@import "${commonPackage}.constant.JsonFieldConst"/>
    @JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
    </#if>
    private ${field.jfieldType} ${jfieldName};

</#macro>
<#--定义宏-查询字段getter-setter模块-->
<#macro queryMethod field alias="">
    <#if alias?hasContent>
        <#assign jfieldName=alias>
    <#else>
        <#assign jfieldName=field.jfieldName>
    </#if>
    public ${field.jfieldType} get${jfieldName?capFirst}() {
        return ${jfieldName};
    }

    public void set${jfieldName?capFirst}(${field.jfieldType} ${jfieldName}) {
        this.${jfieldName} = ${jfieldName};
    }

</#macro>
<#list queryFields as field>
    <#if field.queryType!=QueryType.BETWEEN>
        <@queryField field></@queryField>
    <#else>
        <@queryField field field.jfieldName+"Start"></@queryField>
        <@queryField field field.jfieldName+"End"></@queryField>
    </#if>
</#list>
<#list fields as field>
    <#if field.cascadeQueryExts?? && field.cascadeQueryExts?size &gt; 0>
        <#assign examplePackage="">
        <#if field.foreignEntity!=metaEntity>
            <#assign examplePackage="${packageName}.pojo.example.${field.foreignEntity.className?capFirst}Example.">
        </#if>
        <#list field.cascadeQueryExts as cascadeExt>
            <#assign cascadeField=cascadeExt.cascadeField>
            <#if cascadeField.queryType!=QueryType.BETWEEN>
                <@queryField cascadeField cascadeExt.alias examplePackage></@queryField>
            <#else>
                <@queryField cascadeField cascadeExt.alias+"Start" examplePackage></@queryField>
                <@queryField cascadeField cascadeExt.alias+"End" examplePackage></@queryField>
            </#if>
        </#list>
    </#if>
</#list>

<#list listSortFields as field>
    @ApiModelProperty(notes = "${field.fieldDesc}排序标识【1升序,-1降序,0不排序】",example = "1")
    private Integer ${field.jfieldName}SortSign;

</#list>


<#list queryFields as field>
    <#if field.queryType!=QueryType.BETWEEN>
        <@queryMethod field></@queryMethod>
    <#else>
        <@queryMethod field field.jfieldName+"Start"></@queryMethod>
        <@queryMethod field field.jfieldName+"End"></@queryMethod>
    </#if>
</#list>
<#list fields as field>
    <#if field.cascadeQueryExts?? && field.cascadeQueryExts?size &gt; 0>
        <#list field.cascadeQueryExts as cascadeExt>
            <#assign cascadeField=cascadeExt.cascadeField>
            <#if cascadeField.queryType!=QueryType.BETWEEN>
                <@queryMethod cascadeField cascadeExt.alias></@queryMethod>
            <#else>
                <@queryMethod cascadeField cascadeExt.alias+"Start"></@queryMethod>
                <@queryMethod cascadeField cascadeExt.alias+"End"></@queryMethod>
            </#if>
        </#list>
    </#if>
</#list>
<#list listSortFields as field>
    <@getterSetter2 "${field.jfieldName}SortSign" "Integer"/>
</#list>
}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.qo;

<@printImport/>

${code}
