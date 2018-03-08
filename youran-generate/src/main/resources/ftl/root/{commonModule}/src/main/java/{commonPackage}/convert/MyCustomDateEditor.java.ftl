<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.util.DateUtil"/>
<@import "org.springframework.util.StringUtils"/>
<@import "java.beans.PropertyEditorSupport"/>
<@import "java.util.Date"/>
<@classCom "自定义日期装换"/>
public class MyCustomDateEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        }else {
            setValue(DateUtil.parseDate(text));
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return DateUtil.getDateStr(value);
    }


}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.convert;

<@printImport/>

${code}
