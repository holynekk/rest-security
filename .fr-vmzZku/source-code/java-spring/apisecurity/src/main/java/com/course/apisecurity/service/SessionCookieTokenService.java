package com.course.apisecurity.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.course.apisecurity.constant.SessionCookieConstant;
import com.course.apisecurity.entity.SessionCookieToken;
import com.course.apisecurity.util.HashUtil;
import com.course.apisecurity.util.SecureStringUtil;

@Service
public class SessionCookieTokenService {

	public String store(HttpServletRequest request, SessionCookieToken token) throws NoSuchAlgorithmException {
		// session fixation
		var session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		// session fixation

		session = request.getSession(true);

		session.setAttribute(SessionCookieConstant.SESSION_ATTRIBUTE_USERNAME, token.getUsername());

		// CSRF
		return HashUtil.sha256(session.getId(), token.getUsername());
		// CSRF
	}

	public Optional<SessionCookieToken> read(HttpServletRequest request, String tokenId)
			throws NoSuchAlgorithmException {
		var session = request.getSession(false);

		if (session == null || StringUtils.isBlank(tokenId)) {
			return Optional.empty();
		}

		var username = (String) session.getAttribute(SessionCookieConstant.SESSION_ATTRIBUTE_USERNAME);

		var computedTokenId = HashUtil.sha256(session.getId(), username);

		// prevent timing attack
		if (!SecureStringUtil.equals(tokenId, computedTokenId)) {
			return Optional.empty();
		}

		var token = new SessionCookieToken();
		token.setUsername(username);

		return Optional.of(token);
	}

	public void delete(HttpServletRequest request) {
		var session = request.getSession(false);
		session.invalidate();
	}

}
