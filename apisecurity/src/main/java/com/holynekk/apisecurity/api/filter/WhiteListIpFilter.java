package com.holynekk.apisecurity.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Array;

@Component
public class WhiteListIpFilter extends OncePerRequestFilter {

    private static final String[] ALLOWED_IPS = {"0:0:0:0:0:0:0:1", "10.152.33.216"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!ArrayUtils.contains(ALLOWED_IPS, request.getRemoteAddr())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().print("Forbidden IP: " + request.getRemoteAddr());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
