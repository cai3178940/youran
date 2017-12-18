<#include "/common.ftl">
<#include "/entity_common.ftl">
<#assign importDateKit=false>
<#--定义主体代码-->
<#assign code>
@Component
public class ${CName}Helper {

    @Autowired
    protected ${CName}Service ${cName}Service;

    /**
     * 生成add测试数据
     * @return
     */
    public ${CName}AddDTO get${CName}AddDTO(){
        ${CName}AddDTO dto = new ${CName}AddDTO();
    <#list metaEntity.insertFields as field>
        <#if field.jfieldType==JFieldType.STRING.getJavaType()>
        dto.set${field.jfieldName?capFirst}(E_${field.jfieldName?upperCase});
        <#elseIf field.jfieldType==JFieldType.DATE.getJavaType()>
            <#assign importDateKit=true>
        dto.set${field.jfieldName?capFirst}(DateUtil.parseDate(E_${field.jfieldName?upperCase}));
        <#else>
        dto.set${field.jfieldName?capFirst}(SafeUtil.get${field.jfieldType}(E_${field.jfieldName?upperCase}));
        </#if>
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
    public ${CName}PO save${CName}Example(){
        ${CName}AddDTO addDTO = this.get${CName}AddDTO();
        return ${cName}Service.save(addDTO);
    }



}
</#assign>
<#--开始渲染代码-->
package ${packageName}.help;

import ${packageName}.pojo.dto.*;
import ${packageName}.pojo.po.*;
import ${packageName}.service.*;
<#if importDateKit>
import ${commonPackage}.util.DateUtil;
</#if>
import ${commonPackage}.util.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static ${packageName}.pojo.example.${CName}Example.*;

${code}
