package com.holynekk.apisecurity.api.filter;

import com.holynekk.apisecurity.api.server.auth.basic.BasicAuthApi;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import com.holynekk.apisecurity.util.EncodeDecodeUtil;
import com.holynekk.apisecurity.util.EncryptDecryptUtil;
import com.holynekk.apisecurity.util.HashUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@Order(0)
public class BasicAclUserFilter extends OncePerRequestFilter {

    @Autowired
    private BasicAuthUserRepository basicAuthUserRepository;

    private boolean isValidBasicAuth(String basicAuthString) throws Exception {

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

            return HashUtil.isBcryptMatch(submittedPassword, existingData.get().getPasswordHash());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        var basicAuthString = request.getHeader("Authorization");

        try {
            if (isValidBasicAuth(basicAuthString)) {
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
