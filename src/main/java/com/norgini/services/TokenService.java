package com.norgini.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
	
    @Value("${api.security.token.secret}")
    private String secret;
    @Value("${api.security.token.issuer}")
    private String issuer;
    @Value("${api.security.token.expiration_min}")
    private String expirationMin;

    public String tokenGenerator(UserDetails user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating token.", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime
                .now()
                .plusMinutes(Long.parseLong(expirationMin))
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String getUsername(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT
                    .require(algoritmo)
                    .withIssuer(issuer)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired token.");
		}
	}

}
