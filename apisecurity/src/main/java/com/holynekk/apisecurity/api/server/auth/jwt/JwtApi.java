package com.holynekk.apisecurity.api.server.auth.jwt;

import com.holynekk.apisecurity.entity.JwtData;
import com.holynekk.apisecurity.constant.JwtConstant;
import com.holynekk.apisecurity.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/auth/jwt/v1")
public class JwtApi {

    @Autowired
    private JwtService service;

    @GetMapping("/time")
    public String time(HttpServletRequest request) {
        String encryptedUsername = (String) request.getAttribute(JwtConstant.REQUEST_ATTRIBUTE_USERNAME);
        return "Now is " + LocalTime.now() + ", accessed by " + encryptedUsername;
    }

    @PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String login(HttpServletRequest request) throws Exception {
        String encryptedUsername = (String) request.getAttribute(JwtConstant.REQUEST_ATTRIBUTE_USERNAME);

        JwtData jwtData = new JwtData();
        jwtData.setUsername(encryptedUsername);

        String jwtToken = service.store(jwtData);

        return jwtToken;
    }


}
