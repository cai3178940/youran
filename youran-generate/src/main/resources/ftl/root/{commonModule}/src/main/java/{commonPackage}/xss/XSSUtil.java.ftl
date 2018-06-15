<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "org.jsoup.Jsoup"/>
<@import "org.jsoup.nodes.Document"/>
<@import "org.jsoup.safety.Whitelist"/>
<@classCom "过滤XSS工具"/>
public class XSSUtil {

    public static String clean(String value){
        if(value==null){
            return null;
        }
        //允许base64格式的图片,字符串不进行美化
        return Jsoup.clean(value,"",
            Whitelist.basicWithImages().addProtocols("img","src","data"),
            new Document.OutputSettings().prettyPrint(false));
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.xss;

<@printImport/>

${code}
