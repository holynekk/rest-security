package com.holynekk.apisecurity.api.filter;

import com.holynekk.apisecurity.constant.RedisConstant;
import com.holynekk.apisecurity.entity.JwtData;
import com.holynekk.apisecurity.entity.RedisToken;
import com.holynekk.apisecurity.service.JwtService;
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

public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isValidJwt(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().print("Invalid Token!");
        }
    }

    private boolean isValidJwt(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.startsWithIgnoreCase(authorizationHeader, "Bearer")) {
            return false;
        }

        String bearerToken = StringUtils.substring(authorizationHeader, "Bearer".length() + 1);
        Optional<JwtData> jwtData = jwtService.read(bearerToken);

        if (jwtData.isPresent()) {
            request.setAttribute(RedisConstant.REQUEST_ATTRIBUTE_USERNAME, jwtData.get().getUsername());
            return true;
        } else {
            return false;
        }
    }
}
