package com.holynekk.apisecurity.api.filter.config;

import com.holynekk.apisecurity.api.filter.BasicAuthFilter;
import com.holynekk.apisecurity.api.filter.RedisAuthFilter;
import com.holynekk.apisecurity.api.filter.RedisTokenFilter;
import com.holynekk.apisecurity.api.filter.SessionCookieTokenFilter;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import com.holynekk.apisecurity.service.RedisTokenService;
import com.holynekk.apisecurity.service.SessionCookieTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RedisFilterConfig {

    @Autowired
    private BasicAuthUserRepository basicAuthUserRepository;

    @Autowired
    private RedisTokenService tokenService;

    @Bean
    public FilterRegistrationBean<RedisAuthFilter> redisAuthFilter() {
        FilterRegistrationBean<RedisAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RedisAuthFilter(basicAuthUserRepository));
        registrationBean.addUrlPatterns("/api/auth/redis/v1/login");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RedisTokenFilter> redisTokenFilter() {
        FilterRegistrationBean<RedisTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RedisTokenFilter(tokenService));
        registrationBean.addUrlPatterns("/api/auth/redis/v1/time");
        registrationBean.addUrlPatterns("/api/auth/redis/v1/logout");

        return registrationBean;
    }
}
