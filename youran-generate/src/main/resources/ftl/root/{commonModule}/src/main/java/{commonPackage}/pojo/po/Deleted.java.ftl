<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "是否逻辑删除接口"/>
public interface Deleted {

    Integer getDeleted();

    void setDeleted(Integer deleted);

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
