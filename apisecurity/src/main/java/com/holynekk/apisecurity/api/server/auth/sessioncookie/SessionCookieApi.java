package com.holynekk.apisecurity.api.server.auth.sessioncookie;

import com.holynekk.apisecurity.constant.SessionCookieConstant;
import com.holynekk.apisecurity.entity.SessionCookieToken;
import com.holynekk.apisecurity.service.SessionCookieTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth/session-cookie/v1")
public class SessionCookieApi {

    @Autowired
    private SessionCookieTokenService tokenService;

    @PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String login(HttpServletRequest request) {
        String encryptedUsername = (String) request.getAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME);
        SessionCookieToken token = new SessionCookieToken();
        token.setUsername(encryptedUsername);

        String tokenId = tokenService.store(request, token);

        return tokenId;
    }

    @GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
    public String time(HttpServletRequest request) {
        String encryptedUsername = (String) request.getAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME);

        return "Now is " + LocalDateTime.now() + ", accessed by " + encryptedUsername;
    }

    @DeleteMapping(value = "/logout", produces = MediaType.TEXT_PLAIN_VALUE)
    public String logout(HttpServletRequest request) {
        tokenService.delete(request);

        return "Logged out!";
    }
}
