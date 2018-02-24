<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.pojo.example.AbstractExample"/>
<@classCom "【${title}】参数示例"/>
public class ${CName}Example extends AbstractExample {

<#list metaEntity.fields as field>
    public static final String N_${field.jfieldName?upperCase} = "${field.fieldComment}";
    public static final String E_${field.jfieldName?upperCase} = "${field.fieldExample?replace('\"','\\"')}";
</#list>
}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.example;

<@printImport/>

${code}
