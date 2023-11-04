package com.holynekk.apisecurity.api.server.xss;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

//@RestController
//@RequestMapping("/api/xss/danger/v1")
//@CrossOrigin(origins = "http://localhost:3000")
public class XssSimpleDangerApi {

    @GetMapping(value = "/greeting")
    public String greeting(@RequestParam(value = "name", required = true) String name) {
        int nowHour = LocalTime.now().getHour();

        return (nowHour >= 6 && nowHour < 18) ? ("Good Morning " + name) : ("Good Night " + name);
    }

    @GetMapping(value = "/file")
    public Resource downloadFile() {
        ClassPathResource resource = new ClassPathResource("static/fileWithXss,csv");
        return resource;
    }
}
