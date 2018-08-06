<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.util.JsonUtil")/>
<@call this.addImport("java.io.Serializable")/>
<@call this.printClassCom("抽象VO")/>
public abstract class AbstractVO implements Serializable {

    private static final long serialVersionUID = -1417748095004687576L;

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.vo;

<@call this.printImport()/>

${code}
