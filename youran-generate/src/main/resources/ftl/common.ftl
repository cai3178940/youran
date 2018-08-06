<#-- 调用函数，如果存在输出则打印 -->
<#macro call func><#if func??>${func}</#if></#macro>

<#-- 将当前model赋值给this变量 -->
<#assign this = .data_model>

<#-- 定义基本宏:if1 判断如果属性等于1，则显示内嵌元素-->
<#macro if1 property>
    <#if property?? && property == 1>
        <#nested>
    </#if>
</#macro>

