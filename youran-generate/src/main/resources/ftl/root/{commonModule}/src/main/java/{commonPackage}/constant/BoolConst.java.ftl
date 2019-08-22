<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.validator.Check")/>
<@call this.printClassCom("布尔常量")/>
public class BoolConst {

    // 数据库bool字段值
    public static final int TRUE = 1;

    public static final int FALSE = 0;

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "0,1";

    @Check(message = "只允许输入0或1")
    public static final boolean check(int value) {
        return TRUE == value || FALSE == value;
    }
}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.constant;

<@call this.printImport()/>

${code}
