<#-- 定义基本宏:if1 判断如果属性等于1，则显示内嵌元素-->
<#macro if1 property>
    <#if property?? && property == 1>
        <#nested>
    </#if>
</#macro>


<#-- 定义getter setter模板（根据field） -->
<#macro getterSetter field>
    public ${field.jfieldType} get${field.jfieldName?capFirst}() {
        return ${field.jfieldName};
    }

    public void set${field.jfieldName?capFirst}(${field.jfieldType} ${field.jfieldName}) {
        this.${field.jfieldName} = ${field.jfieldName};
    }

</#macro>
<#-- 定义getter setter模板（指定name、type） -->
<#macro getterSetter2 name type>
    public ${type} get${name?capFirst}() {
        return ${name?uncapFirst};
    }

    public void set${name?capFirst}(${type} ${name?uncapFirst}) {
        this.${name?uncapFirst} = ${name?uncapFirst};
    }

</#macro>
<#-- 定义getter setter模板（list格式） -->
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


<#-- 定义类名截取函数 -->
<#function fetchClassName dicType>
    <#local index=dicType?lastIndexOf(".")/>
    <#if index gt 0>
        <#return dicType?substring(index+1)>
    <#else>
        <#return dicType>
    </#if>
</#function>

<#-- 定义是否通用常量 -->
<#function isCommonPackage dicType>
    <#if dicType == "BoolConst">
        <#return true>
    </#if>
    <#return false>
</#function>
