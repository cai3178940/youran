<#--申明引入包列表-->
<#assign importList=[]>
<#--申明静态引入包列表-->
<#assign importStaticList=[]>
<#--申明autowired类列表-->
<#assign autowiredList=[]>
<#--定义主体代码-->
<#assign code=''>
<#--定义宏：引入依赖包-->
<#macro import package>
    <#if !importList?seqContains(package)>
        <#assign importList = importList + [ package ] />
    </#if>
</#macro>

<#--定义宏：静态引入依赖包-->
<#macro importStatic package>
    <#if !importStaticList?seqContains(package)>
        <#assign importStaticList = importStaticList + [ package ] />
    </#if>
</#macro>

<#--定义宏：打印依赖-->
<#macro printImport>
    <#--首先打印外部依赖-->
    <#list importList?sort as package>
        <#if !package?string?startsWith("java.")&&!package?string?startsWith("javax.")>
import ${package};
        </#if>
    </#list>
    <#local printlnBuildInImport=false>
    <#--再打印java内建依赖-->
    <#list importList?sort as package>
        <#if !printlnBuildInImport>
            <#local printlnBuildInImport=true>

        </#if>
        <#if package?string?startsWith("java.")||package?string?startsWith("javax.")>
import ${package};
        </#if>
    </#list>

    <#list importStaticList?sort as package>
import static ${package};
    </#list>
</#macro>

<#-- 定义Autowired模板 -->
<#macro autowired package className>
    <#if !autowiredList?seqContains(className)>
        <#assign autowiredList = autowiredList + [ className ] />
        <@import "org.springframework.beans.factory.annotation.Autowired"/>
        <@import "${package}.${className}"/>
    @Autowired
    private ${className} ${className?uncapFirst};
    </#if>
</#macro>
