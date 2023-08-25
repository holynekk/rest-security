package com.course.apisecurity.api.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.course.apisecurity.api.filter.JwtAuthFilter;
import com.course.apisecurity.api.filter.JwtFilter;
import com.course.apisecurity.repository.BasicAuthUserRepository;
import com.course.apisecurity.service.JwtService;

@Configuration
public class JwtFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Autowired
	private JwtService jwtService;

	@Bean
	public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
		var registrationBean = new FilterRegistrationBean<JwtAuthFilter>();

		registrationBean.setFilter(new JwtAuthFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/api/auth/jwt/v1/login");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		var registrationBean = new FilterRegistrationBean<JwtFilter>();

		registrationBean.setFilter(new JwtFilter(jwtService));
		registrationBean.addUrlPatterns("/api/auth/jwt/v1/time");
		registrationBean.addUrlPatterns("/api/auth/jwt/v1/logout");

		return registrationBean;
	}

}
