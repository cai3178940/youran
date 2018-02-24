<#include "/common.ftl">
<#include "/const_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.validator.Check"/>
<@import "java.util.HashMap"/>
<@import "java.util.Map"/>
<@classCom "枚举【${remark}】"/>
public enum ${CName} {

<#if constType==1>
    <#assign typeStr="Integer">
<#elseif constType==2>
    <#assign typeStr="String">
</#if>
<#list detailList as detail>
    <#if constType==1>
        <#assign valueStr>${detail.detailValue}</#assign>
    <#elseif constType==2>
        <#assign valueStr>"${detail.detailValue}"</#assign>
    </#if>
    ${detail.detailName}(${valueStr},"${detail.detailRemark}")<#if detail_has_next>,<#else >;</#if>
</#list>


    private final ${typeStr} value;
    private final String desc;

    private static final Map<${typeStr}, ${CName}> lookup = new HashMap<>();

    static {
        for (${CName} e : ${CName}.values()) {
            lookup.put(e.value, e);
        }
    }


    ${CName}(${typeStr} value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ${CName} find(${typeStr} value) {
        return lookup.get(value);
    }

    public static ${CName} findByDesc(String desc){
        for (${CName} e : ${CName}.values()) {
            if(e.getDesc().equals(desc)){
                return e;
            }
        }
        return null;
    }

    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(${typeStr} value){
        ${CName} theEnum = find(value);
        return theEnum!=null;
    }

    public ${typeStr} getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}
</#assign>
<#--开始渲染代码-->
package ${packageName}.constant;

<@printImport/>

${code}
