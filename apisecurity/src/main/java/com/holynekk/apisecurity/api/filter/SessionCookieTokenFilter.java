package com.holynekk.apisecurity.api.filter;

import com.holynekk.apisecurity.constant.SessionCookieConstant;
import com.holynekk.apisecurity.entity.SessionCookieToken;
import com.holynekk.apisecurity.service.SessionCookieTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class SessionCookieTokenFilter extends OncePerRequestFilter {

    private SessionCookieTokenService tokenService;

    public SessionCookieTokenFilter(SessionCookieTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isValidSessionCookie(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().print("Invalid Token!");
        }
    }

    private boolean isValidSessionCookie(HttpServletRequest request) {
        String providedTokenId = request.getHeader("X-CSRF");
        Optional<SessionCookieToken> token = tokenService.read(request, providedTokenId);

        if (token.isPresent()) {
            request.setAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME, token.get().getUsername());
            return true;
        } else {
            return false;
        }
    }
}
