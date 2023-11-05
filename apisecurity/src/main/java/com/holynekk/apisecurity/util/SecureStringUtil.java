package com.holynekk.apisecurity.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureStringUtil {

    private static final String STRING_SEED = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom SECURE_RANDOM;

    static {
        try {
            SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int secureRandomIndex = SECURE_RANDOM.nextInt(STRING_SEED.length());
            sb.append(STRING_SEED.charAt(secureRandomIndex));
        }
        return sb.toString();
    }
}
