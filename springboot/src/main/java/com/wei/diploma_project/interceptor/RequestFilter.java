package com.wei.diploma_project.interceptor;
 
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
/**
 * 解决跨域设置
 *
 */
public class RequestFilter implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.err.println("into 过滤器");

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        
        // 此处 setHeader、addHeader 方法都可用。但 addHeader时写多个会报错：“...,but only one is allowed”
        response.setHeader("Access-Control-Allow-Origin", "*"); 
//        response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        // 解决预请求（发送2次请求），此问题也可在 nginx 中作相似设置解决。
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Cache-Control,Pragma,Content-Type,Token, Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String method = request.getMethod();
        if (method.equalsIgnoreCase("OPTIONS")) {
            servletResponse.getOutputStream().write("Success".getBytes("utf-8"));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
 
    @Override
    public void destroy() {
 
    }
 
}