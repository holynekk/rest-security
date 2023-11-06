package com.holynekk.apisecurity.api.server.auth.acl;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/acl/v1")
public class BasicAclApi {

    @GetMapping(value = "/one", produces = MediaType.TEXT_PLAIN_VALUE)
    public String oneGet() {
        return "Get one";
    }

    @DeleteMapping(value = "/one", produces = MediaType.TEXT_PLAIN_VALUE)
    public String oneDelete() {
        return "DELETE one";
    }

    @PostMapping(value = "/anypath/two", produces = MediaType.TEXT_PLAIN_VALUE)
    public String two() {
        return "Two";
    }

    @PutMapping(value = "/somepath/morepath/three", produces = MediaType.TEXT_PLAIN_VALUE)
    public String three() {
        return "Three";
    }

    @GetMapping(value = "/anypath/evenmorepath/four", produces = MediaType.TEXT_PLAIN_VALUE)
    public String four() {
        return "Four";
    }
}
