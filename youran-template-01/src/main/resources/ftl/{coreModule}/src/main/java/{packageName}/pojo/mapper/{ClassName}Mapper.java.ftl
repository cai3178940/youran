<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ShowVO")/>
<@call this.addImport("org.mapstruct.Mapper")/>
<@call this.addImport("org.mapstruct.MappingTarget")/>
<@call this.addImport("org.mapstruct.factory.Mappers")/>
<@call this.printClassCom("【${this.title}】映射")/>
@Mapper
public interface ${this.classNameUpper}Mapper {

    ${this.classNameUpper}Mapper INSTANCE = Mappers.getMapper( ${this.classNameUpper}Mapper.class );

    /**
    * addDTO映射po
    * @param ${this.className}AddDTO
    * @return
    */
    ${this.classNameUpper}PO fromAddDTO(${this.classNameUpper}AddDTO ${this.className}AddDTO);

    /**
    * 将updateDTO中的值设置到po
    * @param ${this.className}PO
    * @param ${this.className}UpdateDTO
    */
    void setUpdateDTO(@MappingTarget ${this.classNameUpper}PO ${this.className}PO, ${this.classNameUpper}UpdateDTO ${this.className}UpdateDTO);

    /**
    * po映射showVO
    * @param ${this.className}PO
    * @return
    */
    ${this.classNameUpper}ShowVO toShowVO(${this.classNameUpper}PO ${this.className}PO);

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.pojo.mapper;

<@call this.printImport()/>

${code}
