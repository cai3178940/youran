<#-- 功能简介：根据java字段类型，猜测默认值 -->
<#include "/common.ftl">
<#function  guessDefaultJfieldValue jfieldType>
    <#if jfieldType==JFieldType.INTEGER.getJavaType()>
        <#return "0" >
    <#elseIf jfieldType==JFieldType.BOOLEAN.getJavaType()>
        <#return "false" >
    <#elseIf jfieldType==JFieldType.SHORT.getJavaType()>
        <#return "0" >
    <#elseIf jfieldType==JFieldType.LONG.getJavaType()>
        <#return "0L" >
    <#elseIf jfieldType==JFieldType.STRING.getJavaType()>
        <#return "\"\"" >
    <#elseIf jfieldType==JFieldType.DATE.getJavaType()>
        <@call this.addImport("java.util.Date")/>
        <#return "new Date()" >
    <#elseIf jfieldType==JFieldType.DOUBLE.getJavaType()>
        <#return "0.0d" >
    <#elseIf jfieldType==JFieldType.FLOAT.getJavaType()>
        <@call this.addImport("java.util.Date")/>
        <#return "0.0f" >
    <#elseIf jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <@call this.addImport("java.math.BigDecimal")/>
        <#return "BigDecimal.ZERO" >
    </#if>
</#function>
