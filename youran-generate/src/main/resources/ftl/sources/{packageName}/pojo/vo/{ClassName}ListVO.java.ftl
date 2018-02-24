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
        <@import "com.alibaba.fastjson.annotation.JSONField"/>
        <@import "${commonPackage}.constant.JsonFieldConst"/>
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <@import "java.math.BigDecimal"/>
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>

<#list listFields as field>
    <@getterSetter field/>
</#list>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.vo;

<@printImport/>

${code}
