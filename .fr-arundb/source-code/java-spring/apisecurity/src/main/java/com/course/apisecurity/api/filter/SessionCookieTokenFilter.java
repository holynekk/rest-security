package com.course.apisecurity.api.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.course.apisecurity.constant.SessionCookieConstant;
import com.course.apisecurity.service.SessionCookieTokenService;

public class SessionCookieTokenFilter extends OncePerRequestFilter {

	private SessionCookieTokenService tokenService;

	public SessionCookieTokenFilter(SessionCookieTokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			if (isValidSessionCookie(request)) {
				chain.doFilter(request, response);
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.print("{\"message\":\"Invalid token\"}");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isValidSessionCookie(HttpServletRequest request) throws NoSuchAlgorithmException {
		// CSRF
		var csrfToken = request.getHeader("X-CSRF");

		if (StringUtils.isBlank(csrfToken)) {
			return false;
		}
		// CSRF

		var token = tokenService.read(request, csrfToken);

		if (token.isPresent()) {
			request.setAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME, token.get().getUsername());
			return true;
		} else {
			return false;
		}
	}
}
