package com.example.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SecuredFilterConfig {
    //@Autowired
    private JWTFilter jwtFilter;

    //@Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtFilter);
        bean.addUrlPatterns("/api/v1/profile/*");
        bean.addUrlPatterns("/api/v1/region/admin/*");
        bean.addUrlPatterns("/api/v1/category/*");
        bean.addUrlPatterns("/api/v1/articleType/*");
        bean.addUrlPatterns("/api/v1/article/*");
        bean.addUrlPatterns("/api/v1/tag/*");
        bean.addUrlPatterns("/api/v1/emailHistory/admin/*");
        return bean;
    }
}
