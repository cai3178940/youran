<#include "/common.ftl">


<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.pojo.example.AbstractExample")/>
<@call this.printClassCom("【${this.title}】参数示例")/>
public class ${this.classNameUpper}Example extends AbstractExample {

<#list this.metaEntity.fields as field>
    public static final String N_${field.jfieldName?upperCase} = "${field.fieldComment}";
    public static final String E_${field.jfieldName?upperCase} = "${field.fieldExample?replace('\"','\\"')}";
</#list>
}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.pojo.example;

<@call this.printImport()/>

${code}
