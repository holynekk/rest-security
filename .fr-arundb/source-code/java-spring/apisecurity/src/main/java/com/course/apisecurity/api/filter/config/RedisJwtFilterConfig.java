package com.course.apisecurity.api.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.course.apisecurity.api.filter.RedisJwtAuthFilter;
import com.course.apisecurity.api.filter.RedisJwtFilter;
import com.course.apisecurity.repository.BasicAuthUserRepository;
import com.course.apisecurity.service.RedisJwtService;

//@Configuration
public class RedisJwtFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Autowired
	private RedisJwtService jwtService;

	@Bean
	public FilterRegistrationBean<RedisJwtAuthFilter> redisJwtAuthFilter() {
		var registrationBean = new FilterRegistrationBean<RedisJwtAuthFilter>();

		registrationBean.setFilter(new RedisJwtAuthFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/api/auth/redis-jwt/v1/login");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<RedisJwtFilter> redisJwtFilter() {
		var registrationBean = new FilterRegistrationBean<RedisJwtFilter>();

		registrationBean.setFilter(new RedisJwtFilter(jwtService));
		registrationBean.addUrlPatterns("/api/auth/redis-jwt/v1/time");
		registrationBean.addUrlPatterns("/api/auth/redis-jwt/v1/logout");

		return registrationBean;
	}

}
