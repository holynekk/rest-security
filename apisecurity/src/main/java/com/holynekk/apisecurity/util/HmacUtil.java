package com.holynekk.apisecurity.util;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class HmacUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String hmac(String message, String secretKey) throws Exception {
        Mac mac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
        
        mac.init(secretKeySpec);

        byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return new String(Hex.encode(hmacBytes));
    }

    public static boolean isHmacMatch(String message, String secretKey, String hmacValue) throws Exception {
        String reHmacValue = hmac(message, secretKey);
        return StringUtils.equals(reHmacValue, hmacValue);
    }
}
