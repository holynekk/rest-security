package com.holynekk.apisecurity.api.filter;

import com.holynekk.apisecurity.api.server.auth.basic.BasicAuthApi;
import com.holynekk.apisecurity.constant.SessionCookieConstant;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import com.holynekk.apisecurity.util.EncodeDecodeUtil;
import com.holynekk.apisecurity.util.EncryptDecryptUtil;
import com.holynekk.apisecurity.util.HashUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthFilter extends OncePerRequestFilter {

    private BasicAuthUserRepository basicAuthUserRepository;

    public JwtAuthFilter(BasicAuthUserRepository basicAuthUserRepository) {
        this.basicAuthUserRepository = basicAuthUserRepository;
    }

    private boolean isValidBasicAuth(String basicAuthString, HttpServletRequest request) throws Exception {

        if (StringUtils.isBlank(basicAuthString)) {
            return false;
        }

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

            if(HashUtil.isBcryptMatch(submittedPassword, existingData.get().getPasswordHash())) {
                request.setAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME, encryptedUsername);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
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
}
