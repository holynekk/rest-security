package com.holynekk.apisecurity.util;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.util.Base64Utils;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeDecodeUtil {

    public static String encodeBase64(String original) {
        return Base64.getEncoder().encodeToString(original.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeBase64(String encoded) {
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }

    public static String encodeUrl(String original) {
        return UriUtils.encode(original, StandardCharsets.UTF_8);
    }

    public static String decodeUrl(String encoded) {
        return UriUtils.decode(encoded, StandardCharsets.UTF_8);
    }

}
