<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.jsoup.Jsoup")/>
<@call this.addImport("org.jsoup.nodes.Document")/>
<@call this.addImport("org.jsoup.safety.Whitelist")/>
<@call this.printClassCom("过滤XSS工具")/>
public class XSSUtil {

    public static String clean(String value){
        if(value==null){
            return null;
        }
        // 允许base64格式的图片,字符串不进行美化
        return Jsoup.clean(value,"",
            Whitelist.basicWithImages().addProtocols("img","src","data"),
            new Document.OutputSettings().prettyPrint(false));
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.xss;

<@call this.printImport()/>

${code}
