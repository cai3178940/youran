<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "java.util.Date"/>
<@classCom "操作日期接口"/>
public interface OperateDate {

    Date getOperateDate();

    void setOperateDate(Date operateDate);
}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
