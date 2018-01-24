<#assign projectName=projectName?uncapFirst><#--系统名称小写-->
<#assign ProjectName=projectName?capFirst><#--系统名称大写-->

<#-- 定义基本宏:if1 判断如果属性等于1，则显示内嵌元素-->
<#macro if1 property>
    <#if property?? && property == 1>
        <#nested>
    </#if>
</#macro>


<#-- 定义类注释宏 -->
<#macro classCom title="" desc="">
/**
 * Title: ${title}
 * Description: ${desc}
 * Project: ${projectName}
 * Author: ${author}
 * Create Time: ${.now?string("yyyy-MM-dd HH:mm")}
 */
</#macro>

<#-- 定义getter setter方法 -->
<#macro getterSetter field>
    public ${field.jfieldType} get${field.jfieldName?capFirst}() {
        return ${field.jfieldName};
    }

    public void set${field.jfieldName?capFirst}(${field.jfieldType} ${field.jfieldName}) {
        this.${field.jfieldName} = ${field.jfieldName};
    }

</#macro>

<#macro getterSetterList name type>
    public List<${type}> get${name?capFirst}List() {
        return ${name?uncapFirst}List;
    }

    public void set${name?capFirst}List(List<${type}> ${name?uncapFirst}List) {
        this.${name?uncapFirst}List = ${name?uncapFirst}List;
    }

</#macro>

<#-- 定义常量查找函数 -->
<#function getConst list name>
    <#list list as const>
        <#if const.constName==name>
            <#return const>
        </#if>
    </#list>
</#function>
