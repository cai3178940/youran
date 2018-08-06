<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("io.swagger.annotations.ApiParam")/>
<#if this.pageSign == 1>
    <@call this.addImport("${this.commonPackage}.pojo.qo.PageQO")/>
<#else>
    <@call this.addImport("${this.commonPackage}.pojo.qo.AbstractQO")/>
</#if>
<@call this.addStaticImport("${this.packageName}.pojo.example.${this.classNameUpper}Example.*")/>
<@call this.printClassCom("查询【${this.title}】的参数")/>
public class ${this.classNameUpper}QO extends <#if this.pageSign == 1>PageQO<#else>AbstractQO</#if> {

<#--定义宏-查询字段申明模块-->
<#macro queryField field alias="" examplePackage="">
    <#if alias?hasContent>
        <#assign jfieldName=alias>
    <#else>
        <#assign jfieldName=field.jfieldName>
    </#if>
    @ApiParam(value = ${examplePackage}N_${field.jfieldName?upperCase},example = ${examplePackage}E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.STRING.getJavaType()>
        <@call this.addImport("org.hibernate.validator.constraints.Length")/>
    @Length(max = ${field.fieldLength},message = "${field.jfieldName}最大长度不能超过{max}")
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <@call this.addImport("java.util.Date")/>
        <@call this.addImport("com.fasterxml.jackson.annotation.JsonFormat")/>
        <@call this.addImport("${this.commonPackage}.constant.JsonFieldConst")/>
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
    <@call TemplateUtil.printGetterSetter2("${jfieldName}" "${field.jfieldType}")/>
</#macro>
<#list this.queryFields as field>
    <#if field.queryType!=QueryType.BETWEEN>
        <@queryField field></@queryField>
    <#else>
        <@queryField field field.jfieldName+"Start"></@queryField>
        <@queryField field field.jfieldName+"End"></@queryField>
    </#if>
</#list>
<#list this.fields as field>
    <#if field.cascadeQueryExts?? && field.cascadeQueryExts?size &gt; 0>
        <#assign examplePackage="">
        <#if field.foreignEntity!=this.metaEntity>
            <#assign examplePackage="${this.packageName}.pojo.example.${field.foreignEntity.className?capFirst}Example.">
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

<#list this.listSortFields as field>
    @ApiParam(value = "${field.fieldDesc}排序标识【1升序,-1降序,0不排序】",example = "1")
    private Integer ${field.jfieldName}SortSign;

</#list>


<#list this.queryFields as field>
    <#if field.queryType!=QueryType.BETWEEN>
        <@queryMethod field></@queryMethod>
    <#else>
        <@queryMethod field field.jfieldName+"Start"></@queryMethod>
        <@queryMethod field field.jfieldName+"End"></@queryMethod>
    </#if>
</#list>
<#list this.fields as field>
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
<#list this.listSortFields as field>
    <@call TemplateUtil.printGetterSetter2("${field.jfieldName}SortSign" "Integer")/>
</#list>
}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.pojo.qo;

<@call this.printImport()/>

${code}
