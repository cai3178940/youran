<#-- 调用函数，如果存在输出则打印 -->
<#macro call func><#if func??>${func}</#if></#macro>

<#-- 定义布尔常量 -->
<#assign TRUE = 1 />
<#assign FALSE = 0 />

<#-- 定义布尔值判断函数 -->
<#function isTrue arg>
    <#return arg?? && arg == TRUE>
</#function>
<#function isFalse arg>
    <#return !(arg??) || arg == FALSE>
</#function>
<#-- 定义判断相等的函数 -->
<#function equals o1 o2>
    <#return o1! == o2!>
</#function>
<#-- 定义判断不相等的函数 -->
<#function notEquals o1 o2>
    <#return o1! != o2!>
</#function>

<#-- 将当前model赋值给this变量 -->
<#assign this = .data_model>
