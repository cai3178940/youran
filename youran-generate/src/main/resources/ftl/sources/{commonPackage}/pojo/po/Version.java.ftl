<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "是否乐观锁版本接口"/>
public interface Version {

    Integer getVersion();

    void setVersion(Integer version);

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.po;

<@printImport/>

${code}
