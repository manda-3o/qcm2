package com.gestionqcm.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class JwtUtils {
    private static final String SECRET = System.getenv().getOrDefault("GESTION_QCM_JWT_SECRET", "gestionqcm-secret");

    public static String generateToken(String username) {
        String header = base64UrlEncode("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String payload = base64UrlEncode("{\"sub\":\"" + username + "\",\"iss\":\"gestionqcm\"}");
        String signature = sign(header + "." + payload);
        return header + "." + payload + "." + signature;
    }

    public static String validateToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }
        String expected = sign(parts[0] + "." + parts[1]);
        if (!expected.equals(parts[2])) {
            return null;
        }
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        int start = payload.indexOf("\"sub\":\"");
        if (start < 0) {
            return null;
        }
        start += 8;
        int end = payload.indexOf('"', start);
        if (end < 0) {
            return null;
        }
        return payload.substring(start, end);
    }

    private static String sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signature = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String base64UrlEncode(String input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
}
