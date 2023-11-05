package com.holynekk.apisecurity.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holynekk.apisecurity.api.request.util.HmacRequest;
import com.holynekk.apisecurity.api.server.util.HmacApi;
import com.holynekk.apisecurity.util.HmacNonceStorage;
import com.holynekk.apisecurity.util.HmacUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class HmacFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedHttpRequest = new CachedBodyHttpServletRequest((javax.servlet.http.HttpServletRequest) request);
        String nonce = request.getHeader("X-Nonce");

        try {
            if (isValidHmac(cachedHttpRequest, nonce) && HmacNonceStorage.addWhenNotExists(nonce)) {
                filterChain.doFilter((ServletRequest) cachedHttpRequest, response);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.print("{\"message\":\"HMAC signature invalid\"}");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidHmac(CachedBodyHttpServletRequest httpRequest, String nonce) throws Exception {
        var requestBody = objectMapper.readValue(httpRequest.getReader(), HmacRequest.class);
        var xRegisterDate = httpRequest.getHeader("X-Register-Date");
        var hmacFromClient = httpRequest.getHeader("X-Hmac");
        var hmacMessage = HmacApi.constructHmacMessage(httpRequest.getMethod(), httpRequest.getRequestURI(),
                requestBody.getAmount(), requestBody.getFullName(), xRegisterDate, nonce);

        return HmacUtil.isHmacMatch(hmacMessage, HmacApi.SECRET_KEY, hmacFromClient);
    }
}
