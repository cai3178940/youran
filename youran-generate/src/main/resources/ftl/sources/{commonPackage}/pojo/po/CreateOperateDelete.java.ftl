<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "逻辑删除+创建人&创建日期+操作人&操作日期"/>
public interface CreateOperateDelete extends CreateByDate,OperateByDate,DelSign{
}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
