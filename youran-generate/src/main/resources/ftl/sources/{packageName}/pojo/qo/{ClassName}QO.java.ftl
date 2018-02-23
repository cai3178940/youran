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

<#--定义宏-查询字段申明模块-->
<#macro queryField field suffix>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.STRING.getJavaType()>
        <#assign importLength=true>
    @Length(max = ${field.fieldLength},message = "${field.jfieldName}最大长度不能超过{max}")
    <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
        <#assign importDate=true>
    @JSONField(format = JsonFieldConst.DEFAULT_DATETIME_FORMAT)
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
