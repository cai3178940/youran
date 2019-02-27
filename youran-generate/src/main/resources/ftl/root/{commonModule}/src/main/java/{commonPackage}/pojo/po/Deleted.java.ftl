<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("是否逻辑删除接口")/>
public interface Deleted {

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.po;

<@call this.printImport()/>

${code}
