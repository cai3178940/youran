<#include "/common.ftl">
<#include "/entity_common.ftl">

package ${packageName}.pojo.example;

import ${commonPackage}.pojo.example.AbstractExample;

<@classCom "【${title}】参数示例"></@classCom>
public class ${CName}Example extends AbstractExample {

<#list metaEntity.fields as field>
    public static final String N_${field.jfieldName?upperCase} = "${field.fieldComment}";
    public static final String E_${field.jfieldName?upperCase} = "${field.fieldExample?replace('\"','\\"')}";
</#list>
}
