package com.diabetes.auth.security;

import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.Jwts;

public class JwtUtil {

    private static final String SECRET =
            "diabetes-secret-key-32-bytes-long";

    private static final Key KEY =
            new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(KEY)
                .compact();
    }
}