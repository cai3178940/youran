<#include "/common.ftl">
<#include "/entity_common.ftl">
<#--定义是否引入某依赖-->
<#assign importDate=false>
<#assign importBigDecimal=false>
<#--定义主体代码-->
<#assign code>
<@classCom "【${title}】列表展示对象"></@classCom>
@ApiModel(description = "【${title}】列表展示对象")
public class ${CName}ListVO extends AbstractVO {

<#list metaEntity.listFields as field>
    @ApiModelProperty(notes = N_${field.jfieldName?upperCase},example = E_${field.jfieldName?upperCase})
    <#if field.jfieldType==JFieldType.DATE.getJavaType()>
        <#assign importDate=true>
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <#assign importBigDecimal=true>
    </#if>
    private ${field.jfieldType} ${field.jfieldName};

</#list>

<#list metaEntity.listFields as field>
    <@getterSetter field/>
</#list>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if importDate>
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import ${commonPackage}.constant.JsonFieldConst;
</#if>
import ${commonPackage}.pojo.vo.AbstractVO;
<#if importBigDecimal>
import java.math.BigDecimal;
</#if>
import static ${packageName}.pojo.example.${CName}Example.*;

${code}
