<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.pojo.dto.*")/>
<@call this.addImport("${this.packageName}.pojo.po.*")/>
<@call this.addImport("org.springframework.stereotype.Component")/>
<@call this.addStaticImport("${this.packageName}.pojo.example.${this.classNameUpper}Example.*")/>
@Component
public class ${this.classNameUpper}Helper {

    <@call this.addAutowired("${this.packageName}.service" "${this.classNameUpper}Service")/>
    <@call this.printAutowired()/>

    <#--定义外键字段参数串-->
    <#assign foreignArg="">
    <#assign foreignArg2="">
    <#list this.insertFields as id,field>
        <#if field.foreignKey>
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
    public ${this.classNameUpper}AddDTO get${this.classNameUpper}AddDTO(${foreignArg}){
        ${this.classNameUpper}AddDTO dto = new ${this.classNameUpper}AddDTO();
    <#list this.insertFields as id,field>
        <#--字段名转下划线大写-->
        <#assign jfieldNameSnakeCase = MetadataUtil.camelCaseToSnakeCase(field.jfieldName,true)>
        <#assign arg="">
        <#if field.foreignKey>
            <#assign arg="${field.jfieldName}">
        <#elseIf field.jfieldType==JFieldType.STRING.getJavaType()>
            <#assign arg="E_${jfieldNameSnakeCase}">
        <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
            <@call this.addImport("${this.commonPackage}.util.DateUtil")/>
            <#assign arg="DateUtil.parseDate(E_${jfieldNameSnakeCase})">
        <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
            <@call this.addImport("java.math.BigDecimal")/>
            <#assign arg="SafeUtil.get${field.jfieldType}(E_${jfieldNameSnakeCase})">
        <#else>
            <@call this.addImport("${this.commonPackage}.util.SafeUtil")/>
            <#assign arg="SafeUtil.get${field.jfieldType}(E_${jfieldNameSnakeCase})">
        </#if>
        dto.set${field.jfieldName?capFirst}(${arg});
    </#list>
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public ${this.classNameUpper}UpdateDTO get${this.classNameUpper}UpdateDTO(${this.classNameUpper}PO ${this.className}){
        ${this.classNameUpper}UpdateDTO dto = new ${this.classNameUpper}UpdateDTO();
        dto.set${this.idUpper}(${this.className}.get${this.idUpper}());
        <#list this.updateFields as id,field>
        dto.set${field.jfieldName?capFirst}(${this.className}.get${field.jfieldName?capFirst}());
        </#list>
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public ${this.classNameUpper}PO save${this.classNameUpper}Example(${foreignArg}){
        ${this.classNameUpper}AddDTO addDTO = this.get${this.classNameUpper}AddDTO(${foreignArg2});
        return ${this.className}Service.save(addDTO);
    }



}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.help;

<@call this.printImport()/>

${code}
