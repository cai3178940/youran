<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("javax.servlet.*")/>
<@call this.addImport("javax.servlet.http.HttpServletRequest")/>
<@call this.addImport("java.io.IOException")/>
<@call this.printClassCom("防止通过parameter传入XSS脚本")/>
public class WebXSSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 自定义request包装类,并把它传入过滤器链
        XSSRequestWrapper requestWrapper = new XSSRequestWrapper((HttpServletRequest) request);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.xss;

<@call this.printImport()/>

${code}
