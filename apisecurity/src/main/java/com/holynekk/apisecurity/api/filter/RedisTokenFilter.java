package com.holynekk.apisecurity.api.filter;

import com.holynekk.apisecurity.constant.RedisConstant;
import com.holynekk.apisecurity.constant.SessionCookieConstant;
import com.holynekk.apisecurity.entity.RedisToken;
import com.holynekk.apisecurity.entity.SessionCookieToken;
import com.holynekk.apisecurity.service.RedisTokenService;
import com.holynekk.apisecurity.service.SessionCookieTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class RedisTokenFilter extends OncePerRequestFilter {

    private RedisTokenService tokenService;

    public RedisTokenFilter(RedisTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isValidRedis(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().print("Invalid Token!");
        }
    }

    private boolean isValidRedis(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.startsWithIgnoreCase(authorizationHeader, "Bearer")) {
            return false;
        }

        String bearerToken = StringUtils.substring(authorizationHeader, "Bearer".length() + 1);
        Optional<RedisToken> token = tokenService.read(bearerToken);

        if (token.isPresent()) {
            request.setAttribute(RedisConstant.REQUEST_ATTRIBUTE_USERNAME, token.get().getUsername());
            return true;
        } else {
            return false;
        }
    }
}
