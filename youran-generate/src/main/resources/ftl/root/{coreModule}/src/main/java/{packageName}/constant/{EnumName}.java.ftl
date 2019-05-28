<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.validator.Check")/>
<@call this.addImport("com.fasterxml.jackson.annotation.JsonValue")/>
<@call this.addImport("java.util.HashMap")/>
<@call this.addImport("java.util.Map")/>
<@call this.printClassCom("枚举【${this.remark}】")/>
public enum ${this.constNameUpper} {

<#list this.detailList as detail>
    <#if this.constType==MetaConstType.INTEGER>
        <#assign valueStr>${detail.detailValue}</#assign>
    <#elseif this.constType==MetaConstType.STRING>
        <#assign valueStr>"${detail.detailValue}"</#assign>
    </#if>
    /**
     * ${detail.detailRemark}
     */
    ${detail.detailName}(${valueStr},"${detail.detailRemark}")<#if detail_has_next>,<#else >;</#if>
</#list>


    private final ${this.constTypeStr} value;
    private final String desc;

    private static final Map<${this.constTypeStr}, ${this.constNameUpper}> LOOKUP = new HashMap<>();

    static {
        for (${this.constNameUpper} e : ${this.constNameUpper}.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    ${this.constNameUpper}(${this.constTypeStr} value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ${this.constNameUpper} find(${this.constTypeStr} value) {
        return LOOKUP.get(value);
    }

    public static ${this.constNameUpper} findByDesc(String desc){
        for (${this.constNameUpper} e : ${this.constNameUpper}.values()) {
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
    public static final boolean validate(${this.constTypeStr} value){
        ${this.constNameUpper} theEnum = find(value);
        return theEnum!=null;
    }

    @JsonValue
    public ${this.constTypeStr} getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.constant;

<@call this.printImport()/>

${code}
