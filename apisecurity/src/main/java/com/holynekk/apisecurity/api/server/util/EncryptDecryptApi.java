package com.holynekk.apisecurity.api.server.util;

import com.holynekk.apisecurity.api.request.util.OriginalStringRequest;
import com.holynekk.apisecurity.util.EncryptDecryptUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

@RestController
@RequestMapping("/api")
public class EncryptDecryptApi {

    private static final String SECRET_KEY = "MySecretKey12345";

    @GetMapping(value = "/encrypt/aes", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String encryptAes(@RequestBody(required = true) OriginalStringRequest original) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return EncryptDecryptUtil.encryptAes(original.getText(), SECRET_KEY);
    }

    @GetMapping(value = "/decrypt/aes", produces = MediaType.TEXT_PLAIN_VALUE)
    public String decryptAes(@RequestParam(required = true, name = "encrypted") String encrypted) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return EncryptDecryptUtil.decryptAes(encrypted, SECRET_KEY);
    }
}
