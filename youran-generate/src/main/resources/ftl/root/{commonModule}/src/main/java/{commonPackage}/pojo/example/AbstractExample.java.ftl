<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.printClassCom("字段示例")/>
public abstract class AbstractExample {

    public static final String N_CREATEDATE = "创建日期";
    public static final String E_CREATEDATE = "2017-05-22 00:00:00";
    public static final String N_CREATEBY = "创建人";
    public static final String E_CREATEBY = "admin";
    public static final String N_OPERATEDATE = "操作日期";
    public static final String E_OPERATEDATE = "2017-05-22 00:00:00";
    public static final String N_OPERATEBY = "操作人";
    public static final String E_OPERATEBY = "admin";

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.example;

<@call this.printImport()/>

${code}
