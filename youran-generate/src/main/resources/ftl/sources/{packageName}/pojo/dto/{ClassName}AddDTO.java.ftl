<#include "/common.ftl">
<#include "/entity_common.ftl">
<#--定义是否引入某些依赖-->
<#assign importList=false>
<#assign importNotNull=false>
<#assign importLength=false>
<#assign importDate=false>
<#assign importConst=false>
<#assign importConstStr="">
<#--定义主体代码-->
<#assign code>
<@classCom "新增【${title}】的参数"></@classCom>
@ApiModel(description = "新增【${title}】的参数")
public class ${CName}AddDTO extends AbstractDTO {

<#list metaEntity.insertFields as field>

    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <@if1 field.notNull>
    <#assign importNotNull=true>
    @NotNull
    </@if1>
    <#if field.dicType??>
        <#assign importConst=true>
        <#if isCommonPackage(field.dicType)>
            <#assign importConstStr+="import ${commonPackage}.constant.${field.dicType};\n">
        <#else>
            <#assign importConstStr+="import ${packageName}.constant.${field.dicType};\n">
        </#if>
    @Const(constClass = ${fetchClassName(field.dicType)}.class)
    <#elseIf field.jfieldType==JFieldType.STRING.getJavaType()>
        <#assign importLength=true>
    @Length(max = ${field.fieldLength},message = "${field.jfieldName}最大长度不能超过{max}")
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <#assign importDate=true>
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign importList=true>
        <#assign otherPk=otherEntity.pkField>
        <#assign othercName=otherEntity.className?uncapFirst>
    private List<${otherPk.jfieldType}> ${othercName}List;
    </#list>
</#if>

<#list metaEntity.insertFields as field>
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

import ${commonPackage}.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if importNotNull>
import javax.validation.constraints.NotNull;
</#if>
<#if importLength>
import org.hibernate.validator.constraints.Length;
</#if>
<#if importList>
import java.util.List;
</#if>
<#if importDate>
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import ${commonPackage}.constant.JsonFieldConst;
</#if>
<#if importConst>
import ${commonPackage}.validator.Const;
${importConstStr}
</#if>
import static ${packageName}.pojo.example.${CName}Example.*;

${code}
