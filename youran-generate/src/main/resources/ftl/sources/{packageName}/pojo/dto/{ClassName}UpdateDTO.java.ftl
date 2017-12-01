<#include "/common.ftl">
<#include "/entity_common.ftl">
<#--定义是否引入某些依赖-->
<#assign importLength=false>
<#assign importDate=false>
<#assign importConst=false>
<#assign importConstStr="">
<#assign pkField=metaEntity.pkField>
<#--定义主体代码-->
<#assign code>
<@classCom "修改【${title}】的参数"></@classCom>
@ApiModel(description = "新增【${title}】的参数")
public class ${CName}UpdateDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_${pkField.jfieldName?upperCase},example = E_${pkField.jfieldName?upperCase})
    @NotNull
    <#if pkField.jfieldType==JFieldType.STRING.getJavaType()>
        <#assign importLength=true>
    @Length(max = ${(pkField.unicode?? && pkField.unicode == 1)?string(pkField.fieldLength/2,pkField.fieldLength)},message = "${pkField.jfieldName}最大长度不能超过{max}")
    </#if>
    private ${pkField.jfieldType} ${pkField.jfieldName};

<#list metaEntity.updateFields as field>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <@if1 field.notNull>
    @NotNull
    </@if1>
    <#if field.dicType??>
        <#assign importConst=true>
        <#if fetchPackageName(field.dicType)??>
            <#assign importConstStr+="import ${field.dicType};\n">
        <#else>
            <#assign importConstStr+="import ${packageName}.constant.${field.dicType};\n">
        </#if>
    @Const(constClass = ${fetchClassName(field.dicType)}.class)
    <#elseIf field.jfieldType==JFieldType.STRING.getJavaType()>
        <#assign importLength=true>
    @Length(max = ${(field.unicode?? && field.unicode == 1)?string(field.fieldLength/2,field.fieldLength)},message = "${field.jfieldName}最大长度不能超过{max}")
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <#assign importDate=true>
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>

    <@getterSetter pkField/>
<#list metaEntity.updateFields as field>
    <@getterSetter field/>
</#list>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.dto;

import ${commonPackage}.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
<#if importLength>
import org.hibernate.validator.constraints.Length;
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
