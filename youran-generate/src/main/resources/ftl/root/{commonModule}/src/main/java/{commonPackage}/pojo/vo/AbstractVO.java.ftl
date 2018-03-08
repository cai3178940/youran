<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.util.JsonUtil"/>
<@import "java.io.Serializable"/>
<@classCom "抽象VO"/>
public abstract class AbstractVO implements Serializable {

    private static final long serialVersionUID = -1417748095004687576L;

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.vo;

<@printImport/>

${code}
