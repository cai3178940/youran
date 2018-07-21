<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "逻辑删除+创建人&创建日期+操作人&操作日期+版本号"/>
public interface CreatedOperatedDeletedVersion extends Created,Operated,Deleted,Version{
}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
