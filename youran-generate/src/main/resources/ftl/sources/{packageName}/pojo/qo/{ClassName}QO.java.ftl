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
<@classCom "新增【${title}】的参数"/>
@ApiModel(description = "新增【${title}】的参数")
public class ${CName}QO extends <#if pageSign == 1>PageQO<#else>AbstractQO</#if> {

<#--定义宏-查询字段申明模块-->
<#macro queryField field suffix>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.STRING.getJavaType()>
        <@import "org.hibernate.validator.constraints.Length"/>
    @Length(max = ${field.fieldLength},message = "${field.jfieldName}最大长度不能超过{max}")
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <@import "java.util.Date"/>
        <@import "com.fasterxml.jackson.annotation.JsonFormat"/>
        <@import "${commonPackage}.constant.JsonFieldConst"/>
    @JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
    </#if>
    private ${field.jfieldType} ${field.jfieldName}${suffix};

</#macro>
<#--定义宏-查询字段getter-setter模块-->
<#macro queryMethod field suffix>
    public ${field.jfieldType} get${field.jfieldName?capFirst}${suffix}() {
        return ${field.jfieldName}${suffix};
    }

    public void set${field.jfieldName?capFirst}${suffix}(${field.jfieldType} ${field.jfieldName}${suffix}) {
        this.${field.jfieldName}${suffix} = ${field.jfieldName}${suffix};
    }

</#macro>
<#list queryFields as field>
    <#if field.queryType!=QueryType.BETWEEN>
        <@queryField field ""></@queryField>
    <#else>
        <@queryField field "Start"></@queryField>
        <@queryField field "End"></@queryField>
    </#if>
</#list>

<#list listSortFields as field>
    @ApiModelProperty(notes = "${field.fieldDesc}排序标识【1升序,-1降序,0不排序】",example = "1")
    private Integer ${field.jfieldName}SortSign;

</#list>


<#list queryFields as field>
    <#if field.queryType!=QueryType.BETWEEN>
        <@queryMethod field ""></@queryMethod>
    <#else>
        <@queryMethod field "Start"></@queryMethod>
        <@queryMethod field "End"></@queryMethod>
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
