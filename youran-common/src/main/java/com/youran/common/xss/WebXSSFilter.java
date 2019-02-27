package com.youran.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>Title:防止通过parameter传入XSS脚本</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2018/3/2
 */
public class WebXSSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //自定义request包装类,并把它传入过滤器链
        XSSRequestWrapper requestWrapper = new XSSRequestWrapper((HttpServletRequest) request);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
