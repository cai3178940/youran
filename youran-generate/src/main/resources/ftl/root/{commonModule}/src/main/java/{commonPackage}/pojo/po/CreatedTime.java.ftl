<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "java.util.Date"/>
<@classCom "创建日期接口"/>
public interface CreatedTime {

    Date getCreatedTime();

    void setCreatedTime(Date createdTime);

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
