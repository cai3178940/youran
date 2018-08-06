<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("javax.servlet.http.HttpServletRequest")/>
<@call this.addImport("javax.servlet.http.HttpServletRequestWrapper")/>
<@call this.addImport("java.util.HashMap")/>
<@call this.addImport("java.util.Map")/>
<@call this.printClassCom("包装HttpServletRequest，嵌入过滤XSS逻辑")/>
public class XSSRequestWrapper  extends HttpServletRequestWrapper {

    private Map<String , String[]> params = new HashMap<>();

    @SuppressWarnings("unchecked")
    public XSSRequestWrapper(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }

    /**
     * 重写getParameter，代表参数从当前类中的map获取
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        String result = values[0];
        result = clean(result);//转码
        return result;
    }

    public String[] getParameterValues(String name) {//同上
        if(params.get(name) instanceof String[]) {//数组
            int size = (params.get(name)).length;
            String[] vals = new String[size];
            for(int i=0;i<(params.get(name)).length;i++) {
                String str = clean((params.get(name))[i]);//转码
                vals[i] = str;
            }
            return  vals;
        }
        return null;
    }

    private String clean(String value){
        return XSSUtil.clean(value);
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.xss;

<@call this.printImport()/>

${code}
