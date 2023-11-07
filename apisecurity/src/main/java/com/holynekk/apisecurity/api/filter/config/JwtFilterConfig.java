package com.holynekk.apisecurity.api.filter.config;

import com.holynekk.apisecurity.api.filter.JwtAuthFilter;
import com.holynekk.apisecurity.api.filter.JwtFilter;
import com.holynekk.apisecurity.api.filter.RedisAuthFilter;
import com.holynekk.apisecurity.api.filter.RedisTokenFilter;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import com.holynekk.apisecurity.service.JwtService;
import com.holynekk.apisecurity.service.RedisTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

    @Autowired
    private BasicAuthUserRepository basicAuthUserRepository;

    @Autowired
    private JwtService jwtService;

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JwtAuthFilter(basicAuthUserRepository));
        registrationBean.addUrlPatterns("/api/auth/jwt/v1/login");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JwtFilter(jwtService));
        registrationBean.addUrlPatterns("/api/auth/jwt/v1/time");
        registrationBean.addUrlPatterns("/api/auth/jwt/v1/logout");

        return registrationBean;
    }
}
