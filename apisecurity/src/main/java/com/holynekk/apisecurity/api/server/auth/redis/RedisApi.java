package com.holynekk.apisecurity.api.server.auth.redis;

import com.holynekk.apisecurity.constant.RedisConstant;
import com.holynekk.apisecurity.constant.SessionCookieConstant;
import com.holynekk.apisecurity.entity.RedisToken;
import com.holynekk.apisecurity.service.RedisTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/auth/redis/v1")
public class RedisApi {

    @Autowired
    private RedisTokenService tokenService;

    @GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
    public String time(HttpServletRequest request) {
        String encryptedUsername = (String) request.getAttribute(SessionCookieConstant.REQUEST_ATTRIBUTE_USERNAME);
        return "Now is " + LocalTime.now() + " accessed by " + encryptedUsername;
    }

    @PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String login(HttpServletRequest request) {
        String encryptedUsername = (String) request.getAttribute(RedisConstant.REQUEST_ATTRIBUTE_USERNAME);

        RedisToken token = new RedisToken();
        token.setUsername(encryptedUsername);

        String tokenId = tokenService.store(token);
        
        return tokenId;
    }

    @DeleteMapping(value = "/logout", produces = MediaType.TEXT_PLAIN_VALUE)
    public String logout(@RequestHeader(required = true, name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = StringUtils.substring(authorizationHeader, "Bearer".length() + 1);
        tokenService.delete(token);
        return "Logged out!";
    }
}
