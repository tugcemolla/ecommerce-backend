package com.tugce.ecommerce.security;

import com.tugce.ecommerce.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY =
            "ecommerce-projesi-icin-en-az-32-karakter-uzunlugunda-gizli-anahtar";
    private static final long EXPIRATION_TIME =  1000L * 60 * 60 * 24;
    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(User user){
        Date now = new Date();
        Date expirationDate =
                new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigninKey())
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseEncryptedClaims(token)
                .getPayload();
    }

    public String exractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            Date expirationDate =
                    extractAllClaims(token).getExpiration();
            return expirationDate.after(new Date());
        }catch(JwtException | IllegalArgumentException exception){
            return false;
        }
    }
}
