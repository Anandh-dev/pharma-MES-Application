package com.anandh.mes.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Generate JWT
     */
    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);

    }

    /**
     * Generate JWT with claims
     */
    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails) {

        return Jwts.builder()

                .claims(claims)

                .subject(userDetails.getUsername())

                .issuedAt(new Date(System.currentTimeMillis()))

                .expiration(new Date(System.currentTimeMillis()
                        + jwtExpiration))

                .signWith(getSigningKey())

                .compact();

    }

    /**
     * Validate Token
     */
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);

    }

    /**
     * Extract Username
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    /**
     * Extract Expiration
     */
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    /**
     * Extract Claim
     */
    public <T> T extractClaim(

            String token,

            Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);

    }

    /**
     * Extract All Claims
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }

    /**
     * Check Expiration
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token)

                .before(new Date());

    }

    /**
     * Secret Key
     */
    private SecretKey getSigningKey() {

        byte[] keyBytes =

                Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);

    }

}