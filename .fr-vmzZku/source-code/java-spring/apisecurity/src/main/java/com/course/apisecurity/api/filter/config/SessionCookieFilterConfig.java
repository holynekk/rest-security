package com.course.apisecurity.api.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.course.apisecurity.api.filter.SessionCookieAuthFilter;
import com.course.apisecurity.api.filter.SessionCookieTokenFilter;
import com.course.apisecurity.repository.BasicAuthUserRepository;
import com.course.apisecurity.service.SessionCookieTokenService;

//@Configuration
public class SessionCookieFilterConfig {

	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Autowired
	private SessionCookieTokenService tokenService;

	@Bean
	public FilterRegistrationBean<SessionCookieAuthFilter> sessionCookieAuthFilter() {
		var registrationBean = new FilterRegistrationBean<SessionCookieAuthFilter>();

		registrationBean.setFilter(new SessionCookieAuthFilter(basicAuthUserRepository));
		registrationBean.addUrlPatterns("/api/auth/session-cookie/v1/login");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<SessionCookieTokenFilter> sessionCookieTokenFilter() {
		var registrationBean = new FilterRegistrationBean<SessionCookieTokenFilter>();

		registrationBean.setFilter(new SessionCookieTokenFilter(tokenService));
		registrationBean.addUrlPatterns("/api/auth/session-cookie/v1/time");
		registrationBean.addUrlPatterns("/api/auth/session-cookie/v1/logout");

		return registrationBean;
	}

}
