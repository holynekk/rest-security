package com.holynekk.apisecurity.api.filter.config;

import com.holynekk.apisecurity.api.filter.BasicAuthFilter;
import com.holynekk.apisecurity.api.filter.SessionCookieAuthFilter;
import com.holynekk.apisecurity.api.filter.SessionCookieTokenFilter;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import com.holynekk.apisecurity.service.SessionCookieTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionCookieFilterConfig {

    @Autowired
    private BasicAuthUserRepository basicAuthUserRepository;

    @Autowired
    private SessionCookieTokenService tokenService;

    @Bean
    public FilterRegistrationBean<BasicAuthFilter> sessionCookieAuthFilter() {
        FilterRegistrationBean<BasicAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new BasicAuthFilter(basicAuthUserRepository));
        registrationBean.addUrlPatterns("/api/auth/session-cookie/v1/login");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SessionCookieTokenFilter> sessionCookieTokenFilter() {
        FilterRegistrationBean<SessionCookieTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new SessionCookieTokenFilter(tokenService));
        registrationBean.addUrlPatterns("/api/auth/session-cookie/v1/time");
        registrationBean.addUrlPatterns("/api/auth/session-cookie/v1/logout");

        return registrationBean;
    }
}
