<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.validator.Check"/>
<@classCom "布尔常量"/>
public class BoolConst {

    //数据库bool字段值
    public static final int TRUE = 1;

    public static final int FALSE = 0;

    @Check(message = "只允许输入0或1")
    public static final boolean check(int value) {
        return TRUE == value || FALSE == value;
    }
}

</#assign>
<#--开始渲染代码-->
package ${commonPackage}.constant;

<@printImport/>

${code}
