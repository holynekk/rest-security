package com.holynekk.apisecurity.api.filter;

import com.holynekk.apisecurity.api.server.auth.basic.BasicAuthApi;
import com.holynekk.apisecurity.entity.BasicAclUri;
import com.holynekk.apisecurity.entity.BasicAclUserUriRef;
import com.holynekk.apisecurity.entity.BasicAuthUser;
import com.holynekk.apisecurity.repository.BasicAclUriRepository;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import com.holynekk.apisecurity.util.EncodeDecodeUtil;
import com.holynekk.apisecurity.util.EncryptDecryptUtil;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.regex.Pattern;

//@Configuration
//@Order(1)
public class BasicAclUriFilter extends OncePerRequestFilter {

    @Autowired
    private BasicAuthUserRepository userRepository;

    @Autowired
    private BasicAclUriRepository uriRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var basicAuthString = request.getHeader("Authorization");

        var encodedAuthorizationString = StringUtils.substring(basicAuthString, "Basic".length()).trim();
        var plainAuthorizationString = EncodeDecodeUtil.decodeBase64(encodedAuthorizationString);
        var plainAuthorization = plainAuthorizationString.split(":");

        String encryptedUsername = StringUtils.EMPTY;

        try {
            encryptedUsername = EncryptDecryptUtil.encryptAes(plainAuthorization[0], BasicAuthApi.SECRET_KEY);
            var existingUser = userRepository.findByUsername(encryptedUsername).get();

            if (isValidUri(request.getMethod(), request.getRequestURI(), existingUser)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                response.getWriter().print("Method or URI is not allowed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isValidUri(String method, String requestURI, BasicAuthUser existingUser) {
        for (var uriRef: existingUser.getAllowedUris()) {
            BasicAclUri allowedUri = uriRepository.findById(uriRef.getUriId()).get();

            if (StringUtils.equalsIgnoreCase(allowedUri.getMethod(), method) && Pattern.matches(allowedUri.getUri(), requestURI)) {
                return true;
            }
        }

        return false;
    }
}
