<#--申明引入包列表-->
<#assign importList=[]>
<#assign importStaticList=[]>
<#--定义主体代码-->
<#assign code=''>
<#--定义宏：引入依赖包-->
<#macro import package>
    <#if !importList?seq_contains(package)>
        <#assign importList = importList + [ package ] />
    </#if>
</#macro>
<#--定义宏：静态引入依赖包-->
<#macro importStatic package>
    <#if !importStaticList?seq_contains(package)>
        <#assign importStaticList = importStaticList + [ package ] />
    </#if>
</#macro>
<#--定义宏：打印依赖-->
<#macro printImport>
    <#list importList?sort as package>
import ${package};
    </#list>

    <#list importStaticList?sort as package>
import static ${package};
    </#list>
</#macro>

