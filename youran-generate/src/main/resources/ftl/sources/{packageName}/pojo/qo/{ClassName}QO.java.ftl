<#include "/common.ftl">
<#include "/entity_common.ftl">
<#--定义是否引入某些依赖-->
<#assign importLength=false>
<#assign importDate=false>
<#--定义主体代码-->
<#assign code>
<@classCom "新增【${title}】的参数"></@classCom>
@ApiModel(description = "新增【${title}】的参数")
public class ${CName}QO extends <#if pageSign == 1>PageQO<#else>AbstractQO</#if> {

<#macro queryField field suffix>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.STRING.getJavaType()>
        <#assign importLength=true>
    @Length(max = ${field.fieldLength},message = "${field.jfieldName}最大长度不能超过{max}")
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <#assign importDate=true>
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    </#if>
    private ${field.jfieldType} ${field.jfieldName}${suffix};

</#macro>
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

<#list queryFields as field>
    <#if field.queryType!=QueryType.BETWEEN>
        <@queryMethod field ""></@queryMethod>
    <#else>
        <@queryMethod field "Start"></@queryMethod>
        <@queryMethod field "End"></@queryMethod>
    </#if>
</#list>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.qo;
<#if pageSign == 1>
import ${commonPackage}.pojo.qo.PageQO;
<#else>
import ${commonPackage}.pojo.qo.AbstractQO;
</#if>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if importLength>
import org.hibernate.validator.constraints.Length;
</#if>
<#if importDate>
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import ${commonPackage}.constant.JsonFieldConst;
</#if>
import static ${packageName}.pojo.example.${CName}Example.*;

${code}
