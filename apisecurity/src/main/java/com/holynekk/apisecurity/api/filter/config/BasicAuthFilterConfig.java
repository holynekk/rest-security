package com.holynekk.apisecurity.api.filter.config;

import com.holynekk.apisecurity.api.filter.BasicAuthFilter;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class BasicAuthFilterConfig {

    @Autowired
    private BasicAuthUserRepository basicAuthUserRepository;

    @Bean
    public FilterRegistrationBean<BasicAuthFilter> basicAuthFilter() {
        FilterRegistrationBean<BasicAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new BasicAuthFilter(basicAuthUserRepository));
        registrationBean.addUrlPatterns("/api/auth/basic/v1/time");

        return registrationBean;
    }
}
