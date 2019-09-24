<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("io.swagger.annotations.ApiModel")/>
<@call this.addImport("io.swagger.annotations.ApiModelProperty")/>
<@call this.addImport("${this.commonPackage}.pojo.vo.AbstractVO")/>
<@call this.addStaticImport("${this.packageName}.pojo.example.${this.classNameUpper}Example.*")/>
<@call this.printClassCom("【${this.title}】列表展示对象")/>
@ApiModel(description = "【${this.title}】列表展示对象")
public class ${this.classNameUpper}ListVO extends AbstractVO {

<#list this.listFields as field>
    <#--字段名转下划线大写-->
    <#assign jfieldNameSnakeCase = MetadataUtil.camelCaseToSnakeCase(field.jfieldName,true)>
    <#if field.dicType??>
        <@call this.addConstImport(field.dicType)/>
    </#if>
    @ApiModelProperty(notes = N_${jfieldNameSnakeCase},example = E_${jfieldNameSnakeCase}<#if field.dicType??>, allowableValues = ${TemplateUtil.fetchClassName(field.dicType)}.VALUES_STR</#if>)
    <#if field.jfieldType==JFieldType.DATE.getJavaType()>
        <@call this.addImport("java.util.Date")/>
        <@call this.addImport("com.fasterxml.jackson.annotation.JsonFormat")/>
        <@call this.addImport("${this.commonPackage}.constant.JsonFieldConst")/>
    @JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <@call this.addImport("java.math.BigDecimal")/>
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>
<#list this.fkFields as field>
    <#list field.cascadeListExts! as cascadeExt>
        <#assign cascadeField=cascadeExt.cascadeField>
        <#assign examplePackage="">
        <#if cascadeField.dicType??>
            <@call this.addConstImport(cascadeField.dicType)/>
        </#if>
        <#if field.foreignEntity!=this.metaEntity>
            <#assign examplePackage="${this.packageName}.pojo.example.${field.foreignEntity.className?capFirst}Example.">
        </#if>
        <#--字段名转下划线大写-->
        <#assign jfieldNameSnakeCase = MetadataUtil.camelCaseToSnakeCase(cascadeField.jfieldName,true)>
    @ApiModelProperty(notes = ${examplePackage}N_${jfieldNameSnakeCase},example = ${examplePackage}E_${jfieldNameSnakeCase}<#if cascadeField.dicType??>, allowableValues = ${TemplateUtil.fetchClassName(cascadeField.dicType)}.VALUES_STR</#if>)
        <#if cascadeField.jfieldType==JFieldType.DATE.getJavaType()>
            <@call this.addImport("java.util.Date")/>
            <@call this.addImport("com.fasterxml.jackson.annotation.JsonFormat")/>
            <@call this.addImport("${this.commonPackage}.constant.JsonFieldConst")/>
    @JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
        <#elseIf cascadeField.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
            <@call this.addImport("java.math.BigDecimal")/>
        </#if>
    private ${cascadeField.jfieldType} ${cascadeExt.alias};

    </#list>
</#list>
<#list this.holds! as otherEntity,mtm>
    <@call this.addImport("java.util.List")/>
    <#assign otherCName=otherEntity.className/>
    <#assign othercName=otherEntity.className?uncapFirst>
    @ApiModelProperty(notes = "【${otherEntity.title}】列表")
    private List<${otherCName}ListVO> ${othercName}List;

</#list>

<#list this.listFields as field>
    <@call TemplateUtil.printGetterSetter(field)/>
</#list>
<#list this.fkFields as field>
    <#list field.cascadeListExts! as cascadeExt>
        <@call TemplateUtil.printGetterSetter(cascadeExt.alias,cascadeExt.cascadeField.jfieldType)/>
    </#list>
</#list>
<#list this.holds! as otherEntity,mtm>
    <#assign otherCName=otherEntity.className/>
    <#assign othercName=otherEntity.className?uncapFirst>
    <@call TemplateUtil.printGetterSetterList(othercName,"${otherCName}ListVO")/>
</#list>

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.pojo.vo;

<@call this.printImport()/>

${code}
