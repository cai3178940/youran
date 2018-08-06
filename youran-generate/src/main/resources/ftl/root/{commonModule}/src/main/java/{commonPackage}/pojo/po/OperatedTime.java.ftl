<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("java.util.Date")/>
<@call this.printClassCom("操作日期接口")/>
public interface OperatedTime {

    Date getOperatedTime();

    void setOperatedTime(Date operatedTime);
}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.po;

<@call this.printImport()/>

${code}
