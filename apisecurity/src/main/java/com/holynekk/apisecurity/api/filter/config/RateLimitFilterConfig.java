package com.holynekk.apisecurity.api.filter.config;

import com.holynekk.apisecurity.api.filter.RateLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitFilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter_Blue() {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<RateLimitFilter>();

        registrationBean.setFilter(new RateLimitFilter(3));
        registrationBean.setName("rateLimitFilter_Blue");
        registrationBean.addUrlPatterns("/api/dos/v1/blue");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter_Red() {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<RateLimitFilter>();

        registrationBean.setFilter(new RateLimitFilter(2));
        registrationBean.setName("rateLimitFilter_Red");
        registrationBean.addUrlPatterns("/api/dos/v1/red");
        return registrationBean;
    }
}
