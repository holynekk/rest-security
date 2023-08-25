package com.course.apisecurity.api.filter.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.course.apisecurity.api.filter.RateLimitFilter;

//@Configuration
public class RateLimitFilterConfig {

	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilter_Blue() {
		var registrationBean = new FilterRegistrationBean<RateLimitFilter>();

		registrationBean.setFilter(new RateLimitFilter(3));
		registrationBean.setName("rateLimitFilter_Blue");
		registrationBean.addUrlPatterns("/api/dos/v1/blue");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilter_Red() {
		var registrationBean = new FilterRegistrationBean<RateLimitFilter>();

		registrationBean.setFilter(new RateLimitFilter(2));
		registrationBean.setName("rateLimitFilter_Red");
		registrationBean.addUrlPatterns("/api/dos/v1/red");

		return registrationBean;
	}

}
