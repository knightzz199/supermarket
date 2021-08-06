package com.kxt.supermarket.config;

import com.kxt.supermarket.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Kxt
 * @Date 2021/8/6 1:15
 * @Version 1.0
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                        "/user/register","/admin/register","/user/login","/admin/login",
                        "/goods/getAllGoodsUpPrice","//goods/getAllGoodsDownPrice",
                        "/goods/getAllGoodsBySale","/goods/getAllGoodsByAdminId");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
