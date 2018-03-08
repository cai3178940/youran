<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${packageName}.pojo.dto.*"/>
<@import "${packageName}.pojo.po.*"/>
<@import "org.springframework.stereotype.Component"/>
<@importStatic "${packageName}.pojo.example.${CName}Example.*"/>
@Component
public class ${CName}Helper {

    <@autowired "${packageName}.service" "${CName}Service"/>

    <#--定义外键字段参数串-->
    <#assign foreignArg="">
    <#assign foreignArg2="">
    <#list insertFields as field>
        <#if field.foreignKey==1>
            <#assign foreignArg=foreignArg+"${field.jfieldType} ${field.jfieldName}, ">
            <#assign foreignArg2=foreignArg2+"${field.jfieldName}, ">
        </#if>
    </#list>
    <#if foreignArg?length gt 0>
        <#assign foreignArg=foreignArg?substring(0,foreignArg?length-2)>
        <#assign foreignArg2=foreignArg2?substring(0,foreignArg2?length-2)>
    </#if>
    /**
     * 生成add测试数据
     * @return
     */
    public ${CName}AddDTO get${CName}AddDTO(${foreignArg}){
        ${CName}AddDTO dto = new ${CName}AddDTO();
    <#list metaEntity.insertFields as field>
        <#assign arg="">
        <#if field.foreignKey==1>
            <#assign arg="${field.jfieldName}">
        <#elseIf field.jfieldType==JFieldType.STRING.getJavaType()>
            <#assign arg="E_${field.jfieldName?upperCase}">
        <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
            <@import "${commonPackage}.util.DateUtil"/>
            <#assign arg="DateUtil.parseDate(E_${field.jfieldName?upperCase})">
        <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
            <@import "java.math.BigDecimal"/>
            <#assign arg="SafeUtil.get${field.jfieldType}(E_${field.jfieldName?upperCase})">
        <#else>
            <@import "${commonPackage}.util.SafeUtil"/>
            <#assign arg="SafeUtil.get${field.jfieldType}(E_${field.jfieldName?upperCase})">
        </#if>
        dto.set${field.jfieldName?capFirst}(${arg});
    </#list>
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public ${CName}UpdateDTO get${CName}UpdateDTO(${CName}PO ${cName}){
        ${CName}UpdateDTO dto = new ${CName}UpdateDTO();
        dto.set${Id}(${cName}.get${Id}());
        <#list metaEntity.updateFields as field>
        dto.set${field.jfieldName?capFirst}(${cName}.get${field.jfieldName?capFirst}());
        </#list>
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public ${CName}PO save${CName}Example(${foreignArg}){
        ${CName}AddDTO addDTO = this.get${CName}AddDTO(${foreignArg2});
        return ${cName}Service.save(addDTO);
    }



}
</#assign>
<#--开始渲染代码-->
package ${packageName}.help;

<@printImport/>

${code}
