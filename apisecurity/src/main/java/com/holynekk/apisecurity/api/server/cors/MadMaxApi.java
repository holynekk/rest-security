package com.holynekk.apisecurity.api.server.cors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cors/v1")
public class MadMaxApi {
    @GetMapping(value = "/mad", produces = MediaType.TEXT_PLAIN_VALUE)
//	@CrossOrigin(origins = "http://127.0.0.1:8080")
    private String mad() {
        return "Mad";
    }

    @PostMapping(value = "/max", produces = MediaType.TEXT_PLAIN_VALUE)
//	@CrossOrigin(origins = { "http://127.0.0.1:8080", "http://192.168.0.8:8080" })
    private String max() {
        return "Max";
    }
}
