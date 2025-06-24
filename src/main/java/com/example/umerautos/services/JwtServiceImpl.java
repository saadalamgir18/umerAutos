package com.example.umerautos.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.secret-key}")
    private String SECRET;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;


    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(this.SECRET.getBytes(StandardCharsets.UTF_8));

    }

    public String getUsername(String token) {
        return extractPayload(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        return (List<String>) extractPayload(token).get("role");
    }

    @Override
    public Claims extractPayload(String token) {

        return Jwts
                .parser().
                verifyWith(this.getKey())
                .build().parseSignedClaims(token).getPayload();
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractPayload(token);

        return claimsTFunction.apply(claims);
    }

    @Override
    public String generateToken(Authentication authentication) {

        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String username = authentication.getName();

        return Jwts.builder()
                .subject(username)
                .claim("role", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(new Date().getTime() + jwtExpiration * 1000L))
                .signWith(this.getKey())
                .compact();
    }

    public Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }

    @Override
    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parse(token);

            return true;

        } catch (Exception malformedJwtException) {
            throw new MalformedJwtException("Invalid JWT Token");
        }

    }


    public String extractEmail(String token) {

        return this.extractClaim(token, Claims::getSubject);
    }

    public String getToken(HttpServletRequest request) {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;

    }
}
