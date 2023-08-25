package com.course.apisecurity.api.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.course.apisecurity.api.filter.BasicAuthFilter;
import com.course.apisecurity.repository.BasicAuthUserRepository;

//@Configuration
public class BasicAuthFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Bean
	public FilterRegistrationBean<BasicAuthFilter> basicAuthFilter() {
		var registrationBean = new FilterRegistrationBean<BasicAuthFilter>();

		registrationBean.setFilter(new BasicAuthFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/api/auth/basic/v1/time");

		return registrationBean;
	}

}
