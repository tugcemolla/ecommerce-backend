package com.tugce.ecommerce.security;

import com.tugce.ecommerce.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "ecommerce-projesi-icin-en-az-32-karakter-uzunlugunda-gizli-anahtar";

    private static final long EXPIRATION_TIME =
            1000L * 60 * 60 * 24;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(User user) {

        Date now = new Date();

        Date expirationDate =
                new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token)
                .get("role", String.class);
    }

    public boolean isTokenValid(String token) {

        try {
            Date expirationDate =
                    extractAllClaims(token).getExpiration();

            System.out.println("TOKEN GEÇERLİ");
            System.out.println("BİTİŞ TARİHİ: " + expirationDate);

            return expirationDate.after(new Date());

        } catch (JwtException | IllegalArgumentException exception) {

            System.out.println(
                    "TOKEN HATASI: " + exception.getMessage()
            );

            exception.printStackTrace();

            return false;
        }
    }
}