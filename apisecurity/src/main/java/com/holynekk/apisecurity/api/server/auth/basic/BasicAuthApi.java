package com.holynekk.apisecurity.api.server.auth.basic;

import com.holynekk.apisecurity.api.request.auth.basic.BasicAuthCreateUserRequest;
import com.holynekk.apisecurity.entity.BasicAuthUser;
import com.holynekk.apisecurity.repository.BasicAuthUserRepository;
import com.holynekk.apisecurity.util.EncryptDecryptUtil;
import com.holynekk.apisecurity.util.HashUtil;
import com.holynekk.apisecurity.util.SecureStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/auth/basic/v1")
public class BasicAuthApi {

    public static final String SECRET_KEY = "TheSecretKey2468";

    @Autowired
    private BasicAuthUserRepository repository;

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createUser(@RequestBody BasicAuthCreateUserRequest userRequest) throws Exception {
        var user = new BasicAuthUser();

        var encryptedUsername = EncryptDecryptUtil.encryptAes(userRequest.getUsername().toLowerCase(), SECRET_KEY);
        user.setUsername(encryptedUsername);

        var salt = SecureStringUtil.randomString(16);
        user.setSalt(salt);

        var passwordHash = HashUtil.bcrypt(userRequest.getPassword(), salt);
        user.setPasswordHash(passwordHash);

        user.setDisplayName(userRequest.getDisplayName());

        var saved = repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("New user created : " + saved.getDisplayName());
    }

    @GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
    public String time() {
        return "Now is " + LocalTime.now();
    }
}
