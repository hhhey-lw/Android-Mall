package com.wei.diploma_project.util;

import com.wei.diploma_project.interceptor.LoginInterceptor;
import com.wei.diploma_project.interceptor.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* @Configuration 标注为配置类，在spring容器初始化时，载入该配置
* */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean filter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("RequestFilter");
        //设置优先级别
        registration.setOrder(1);
        return registration;
    }

    //  处理跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 拦截所有的请求
                .allowedOriginPatterns("*")  // 可跨域的域名 为本机ipv4 http://localhost:8080
                .allowedHeaders("*")  // 允许 头部 携带信息
                .allowedMethods("*")   // 允许跨域的方法，可以单独配置
                .allowCredentials(true);  // 允许跨域的请求头，可以单独配置
    }

    //  配置静态资源路径 就是图片...路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/*")
                .addResourceLocations("classpath:/static/image/")
                .addResourceLocations("file:C:\\Users\\韦龙\\Desktop\\毕设\\springboot\\src\\main\\resources\\static\\image\\");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/user/login", "/user/verifyCode", "/banner/**",
                "/good/**", "/comment/all/**", "/rec/**", "/image/**", "/user/code/**", "/user/login/**");
    }
}