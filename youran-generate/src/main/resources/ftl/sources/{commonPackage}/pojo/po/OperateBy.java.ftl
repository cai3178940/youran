<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "操作人接口"/>
public interface OperateBy {

    String getOperateBy();

    void setOperateBy(String operateBy);

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
