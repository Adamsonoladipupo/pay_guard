package com.pay_guard.pay_guard_bkd.security;

import com.pay_guard.pay_guard_bkd.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{
    private final JwtProperties properties;

    private final SecretKey key;

    public JwtServiceImpl(JwtProperties properties) {
        this.properties = properties;

        this.key = Keys.hmacShaKeyFor(
                properties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()

                .subject(userDetails.getUsername())

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + properties.getExpiration()
                        )
                )
                .signWith(key)
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    @Override
    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {
        String username =
                extractUsername(token);
        return username.equals(userDetails.getUsername())
                &&
                !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractClaim(
                token,
                Claims::getExpiration
        ).before(new Date());
    }

    private <T> T extractClaim(
            String token,
            Function<Claims, T> resolver
    ) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }
}
