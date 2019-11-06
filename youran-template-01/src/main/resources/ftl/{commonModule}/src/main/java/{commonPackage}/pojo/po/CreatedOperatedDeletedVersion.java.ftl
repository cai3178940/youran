<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("逻辑删除+创建人&创建日期+操作人&操作日期+版本号")/>
public interface CreatedOperatedDeletedVersion extends Created,Operated,Deleted,Version{
}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.po;

<@call this.printImport()/>

${code}
