package com.kxt.supermarket.interceptor;

import com.kxt.supermarket.pojo.Admin;
import com.kxt.supermarket.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kxt
 * @Date 2021/8/6 1:11
 * @Version 1.0
 */

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        User user = (User) request.getSession().getAttribute("user");
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        return user != null || admin != null;
    }
}
