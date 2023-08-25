package com.course.apisecurity.api.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.course.apisecurity.api.server.auth.basic.BasicAuthApi;
import com.course.apisecurity.constant.SessionCookieConstant;
import com.course.apisecurity.repository.BasicAuthUserRepository;
import com.course.apisecurity.util.EncodeDecodeUtil;
import com.course.apisecurity.util.EncryptDecryptUtil;
import com.course.apisecurity.util.HashUtil;

public class RedisAuthFilter extends OncePerRequestFilter {

	private BasicAuthUserRepository basicAuthUserRepository;

	public RedisAuthFilter(BasicAuthUserRepository basicAuthUserRepository) {
		this.basicAuthUserRepository = basicAuthUserRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		var basicAuthString = request.getHeader("Authorization");

		try {
			if (isValidBasicAuth(basicAuthString, request)) {
				chain.doFilter(request, response);
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				PrintWriter writer = response.getWriter();
				writer.print("{\"message\":\"Invalid credential\"}");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isValidBasicAuth(String basicAuthString, HttpServletRequest request) throws Exception {
		try {
			var encodedAuthorizationString = StringUtils.substring(basicAuthString, "Basic".length()).trim();
			var plainAuthorizationString = EncodeDecodeUtil.decodeBase64(encodedAuthorizationString);
			var plainAuthorization = plainAuthorizationString.split(":");

			var encryptedUsername = EncryptDecryptUtil.encryptAes(plainAuthorization[0], BasicAuthApi.SECRET_KEY);
			var submittedPassword = plainAuthorization[1];

			var existingData = basicAuthUserRepository.findByUsername(encryptedUsername);

			if (existingData.isEmpty()) {
				return false;
			}

			if (HashUtil.isBcryptMatch(submittedPassword, existingData.get().getPasswordHash())) {
				request.setAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME, encryptedUsername);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
