package com.tan.best.configuration.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
* 自定义拦截器
*/
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
    response, Object handler) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        log.info("===========preHandle -requestURL ：{}=========", requestURL);
//        System.out.println("====     System      拦截到了方法：{}，在该方法执行之前执行====");
        // 返回true才会继续执行，返回false则取消当前请求
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse
    response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("========postHandle=======");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse
    response, Object handler, Exception ex) throws Exception {
        log.info("=============afterCompletion============");
    }
}