<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "io.swagger.annotations.ApiModel"/>
<@import "io.swagger.annotations.ApiModelProperty"/>
<@import "${commonPackage}.pojo.vo.AbstractVO"/>
<@importStatic "${packageName}.pojo.example.${CName}Example.*"/>
<@classCom "【${title}】列表展示对象"/>
@ApiModel(description = "【${title}】列表展示对象")
public class ${CName}ListVO extends AbstractVO {

<#list listFields as field>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.DATE.getJavaType()>
        <@import "java.util.Date"/>
        <@import "com.fasterxml.jackson.annotation.JsonFormat"/>
        <@import "${commonPackage}.constant.JsonFieldConst"/>
    @JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <@import "java.math.BigDecimal"/>
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>
<#list fields as field>
    <#if field.cascadeListExts?? && field.cascadeListExts?size &gt; 0>
        <#list field.cascadeListExts as cascadeExt>
            <#assign cascadeField=cascadeExt.cascadeField>
            <#assign examplePackage="">
            <#if field.foreignEntity!=metaEntity>
                <#assign examplePackage="${packageName}.pojo.example.${field.foreignEntity.className?capFirst}Example.">
            </#if>
    @ApiModelProperty(notes = ${examplePackage}N_${cascadeField.jfieldName?upperCase},example = ${examplePackage}E_${cascadeField.jfieldName?upperCase})
            <#if cascadeField.jfieldType==JFieldType.DATE.getJavaType()>
                <@import "java.util.Date"/>
                <@import "com.fasterxml.jackson.annotation.JsonFormat"/>
                <@import "${commonPackage}.constant.JsonFieldConst"/>
    @JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
            <#elseIf cascadeField.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
                <@import "java.math.BigDecimal"/>
            </#if>
    private ${cascadeField.jfieldType} ${cascadeExt.alias};

        </#list>
    </#if>
</#list>

<#list listFields as field>
    <@getterSetter field/>
</#list>
<#list fields as field>
    <#if field.cascadeListExts?? && field.cascadeListExts?size &gt; 0>
        <#list field.cascadeListExts as cascadeExt>
            <@getterSetter2 cascadeExt.alias cascadeExt.cascadeField.jfieldType/>
        </#list>
    </#if>
</#list>


}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.vo;

<@printImport/>

${code}
