package xyz.necmettincimen.marvel.marvel.config;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    private SecretKey key;

    private static final String CLAIM_ID = "id";

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getEncoder().encode(secret.getBytes());
        this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    public String generateToken(String username, String id) {
        return Jwts.builder()
                .subject(username)
                .claim("authorities", "ROLE_USER")
                .claim(CLAIM_ID, id)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username));
    }

    public Long extractId(String currentToken) {
        return Long.parseLong(
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(currentToken)
                        .getPayload()
                        .getOrDefault(CLAIM_ID, "0").toString());
    }
}
