package com.kaonstudio.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class JwtService {

    private final SecretKey secretKey;

    public String generateEmailConfirmationToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(secretKey)
                .compact();
    }

    public String generateAuthJwtToken(String email, String id) {
        return Jwts.builder()
                .setIssuer(id)
                .setSubject(email)
                .setIssuedAt(new java.util.Date())
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(14)))
                .signWith(secretKey)
                .compact();
    }

}
