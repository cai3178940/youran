<#include "/common.ftl">
<#include "/entity_common.ftl">

package ${packageName}.pojo.example;

<@classCom "【${title}】参数示例"></@classCom>
public class ${CName}Example {

<#list metaEntity.fields as field>
    public static final String N_${field.jfieldName?upper_case} = "${field.fieldComment}";
    public static final String E_${field.jfieldName?upper_case} = "${field.fieldExample?replace('\"','\\"')}";
</#list>
}
