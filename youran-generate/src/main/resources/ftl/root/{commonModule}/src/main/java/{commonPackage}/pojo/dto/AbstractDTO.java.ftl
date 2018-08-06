<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.util.JsonUtil")/>
<@call this.addImport("java.io.Serializable")/>
<@call this.printClassCom("数据传输对象超类")/>
public abstract class AbstractDTO implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.dto;

<@call this.printImport()/>

${code}
