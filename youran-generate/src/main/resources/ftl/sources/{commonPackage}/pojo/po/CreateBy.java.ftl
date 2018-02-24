<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "创建人接口"/>
public interface CreateBy {

    String getCreateBy();

    void setCreateBy(String createBy);

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
