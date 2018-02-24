<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "io.swagger.annotations.ApiModel"/>
<@import "io.swagger.annotations.ApiModelProperty"/>
<@import "${commonPackage}.pojo.vo.AbstractVO"/>
<@importStatic "${packageName}.pojo.example.${CName}Example.*"/>
<@classCom "【${title}】详情展示对象"/>
@ApiModel(description = "【${title}】详情展示对象")
public class ${CName}ShowVO extends AbstractVO {

<#list showFields as field>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.DATE.getJavaType()>
        <@import "java.util.Date"/>
        <@import "com.alibaba.fastjson.annotation.JSONField"/>
        <@import "${commonPackage}.constant.JsonFieldConst"/>
    @JSONField(format = JsonFieldConst.DEFAULT_DATETIME_FORMAT)
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <@import "java.math.BigDecimal"/>
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <@import "java.util.List"/>
        <#assign otherCName=otherEntity.className/>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherType=otherEntity.pkField.jfieldType>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    @ApiModelProperty(notes = "【${otherEntity.title}】列表")
    private List<${otherCName}ListVO> ${othercName}List;
    </#list>
</#if>

<#list showFields as field>
    <@getterSetter field/>
</#list>

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className/>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherListVO="${otherCName}ListVO">
        <@getterSetterList othercName otherListVO/>
    </#list>
</#if>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.vo;

<@printImport/>

${code}
