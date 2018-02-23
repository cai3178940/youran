<#include "/common.ftl">
<#include "/entity_common.ftl">
<#--定义是否引入某依赖-->
<#assign importDate=false>
<#assign importList=false>
<#assign importBigDecimal=false>
<#--定义主体代码-->
<#assign code>
<@classCom "【${title}】详情展示对象"></@classCom>
@ApiModel(description = "【${title}】详情展示对象")
public class ${CName}ShowVO extends AbstractVO {

<#list showFields as field>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.DATE.getJavaType()>
        <#assign importDate=true>
    @JSONField(format = JsonFieldConst.DEFAULT_DATETIME_FORMAT)
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <#assign importBigDecimal=true>
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign importList=true>
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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if importBigDecimal>
import java.math.BigDecimal;
</#if>
<#if importList>
import java.util.List;
</#if>
<#if importDate>
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import ${commonPackage}.constant.JsonFieldConst;
</#if>
import ${commonPackage}.pojo.vo.AbstractVO;

import static ${packageName}.pojo.example.${CName}Example.*;

${code}
