package com.holynekk.apisecurity.api.server.dos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dos/v1")
public class RedGreenBlueApi {

    private static final Logger LOG = LoggerFactory.getLogger(RedGreenBlueApi.class);

    @GetMapping(value = "/green")
    public String green() {
        LOG.info("green");
        return "green";
    }

    @GetMapping(value = "/blue")
    public String blue() {
        LOG.info("blue");
        return "blue";
    }

    @GetMapping(value = "/red")
    public String red() {
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {

        }
        LOG.info("red");
        return "red";
    }
}
