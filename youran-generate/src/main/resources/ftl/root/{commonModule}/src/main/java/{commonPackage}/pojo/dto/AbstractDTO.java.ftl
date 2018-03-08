<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.util.JsonUtil"/>
<@import "java.io.Serializable"/>
<@classCom "数据传输对象超类"/>
public abstract class AbstractDTO implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.dto;

<@printImport/>

${code}
