<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.pojo.dto.AbstractDTO"/>
<@import "io.swagger.annotations.ApiModel"/>
<@import "io.swagger.annotations.ApiModelProperty"/>
<@importStatic "${packageName}.pojo.example.${CName}Example.*"/>
<@classCom "新增【${title}】的参数"/>
@ApiModel(description = "新增【${title}】的参数")
public class ${CName}AddDTO extends AbstractDTO {

<#list insertFields as field>

    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase}<@if1 field.notNull>,required = true</@if1>)
    <@if1 field.notNull>
        <@import "javax.validation.constraints.NotNull"/>
    @NotNull
    </@if1>
    <#if field.dicType??>
        <@import "${commonPackage}.validator.Const"/>
        <#if isCommonPackage(field.dicType)>
            <@import "${commonPackage}.constant.${field.dicType}"/>
        <#else>
            <@import "${packageName}.constant.${field.dicType}"/>
        </#if>
    @Const(constClass = ${fetchClassName(field.dicType)}.class)
    <#elseIf field.jfieldType==JFieldType.STRING.getJavaType()>
        <@import "org.hibernate.validator.constraints.Length"/>
    @Length(max = ${field.fieldLength})
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <@import "java.util.Date"/>
        <@import "com.fasterxml.jackson.annotation.JsonFormat"/>
        <@import "${commonPackage}.constant.JsonFieldConst"/>
    @JsonFormat(pattern=JsonFieldConst.DEFAULT_DATE_FORMAT,timezone="GMT+8")
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <@import "java.math.BigDecimal"/>
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <@import "java.util.List"/>
        <#assign otherPk=otherEntity.pkField>
        <#assign othercName=otherEntity.className?uncapFirst>
    private List<${otherPk.jfieldType}> ${othercName}List;
    </#list>
</#if>

<#list insertFields as field>
    <@getterSetter field/>
</#list>

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign othercName=otherEntity.className?uncapFirst>
        <@getterSetterList othercName otherPk.jfieldType/>
    </#list>
</#if>
}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.dto;

<@printImport/>

${code}

