package com.tan.best.configuration;

import com.tan.best.configuration.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.util.concurrent.TimeUnit;

@Configuration

public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/*.html");
    }
}
