package com.wei.diploma_project.interceptor;

import com.wei.diploma_project.exception.NotFoundException;
import com.wei.diploma_project.exception.NotLoginException;
import com.wei.diploma_project.exception.TokenException;
import com.wei.diploma_project.util.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: 韦龙
 * Date: 2023/4/22
 * description:
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (response.getStatus() == 404)
            throw new NotFoundException("拦截 " + request.getRequestURL());
        System.err.println("token : " + request.getHeader("token"));
        if (request.getHeader("token").equals("") || request.getHeader("token") == null)
            throw new NotLoginException();
        else {
            if (!JWTUtil.verify(request.getHeader("token"))) {
                throw new TokenException();
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
