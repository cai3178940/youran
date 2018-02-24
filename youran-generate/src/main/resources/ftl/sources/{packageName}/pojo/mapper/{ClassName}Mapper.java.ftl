<#include "/common.ftl">
<#include "/entity_common.ftl">
<#--定义是否引入某依赖-->
<#assign code>
<@classCom "【${title}】映射"/>
@Mapper
public interface ${CName}Mapper {

    ${CName}Mapper INSTANCE = Mappers.getMapper( ${CName}Mapper.class );

    /**
    * addDTO映射po
    * @param ${cName}AddDTO
    * @return
    */
    ${CName}PO fromAddDTO(${CName}AddDTO ${cName}AddDTO);

    /**
    * 将updateDTO中的值设置到po
    * @param ${cName}PO
    * @param ${cName}UpdateDTO
    */
    void setUpdateDTO(@MappingTarget ${CName}PO ${cName}PO, ${CName}UpdateDTO ${cName}UpdateDTO);

    /**
    * po映射showVO
    * @param ${cName}PO
    * @return
    */
    ${CName}ShowVO toShowVO(${CName}PO ${cName}PO);

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.mapper;

import ${packageName}.pojo.dto.${CName}AddDTO;
import ${packageName}.pojo.dto.${CName}UpdateDTO;
import ${packageName}.pojo.po.${CName}PO;
import ${packageName}.pojo.vo.${CName}ShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


${code}
