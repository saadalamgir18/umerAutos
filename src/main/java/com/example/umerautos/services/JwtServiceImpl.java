package com.example.umerautos.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);


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


        try {
            return Jwts
                    .parser().
                    verifyWith(this.getKey())
                    .build().parseSignedClaims(token).getPayload();

        } catch (Exception malformedJwtException) {
            logger.error("Invalid JWT Token", malformedJwtException);
            throw new MalformedJwtException("Invalid JWT Token");
        }
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractPayload(token);

        return claimsTFunction.apply(claims);
    }

    @Override
    public String generateAccessToken(UserDetails user) {
        logger.info("Generating access token for user: {}", user.getUsername());
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String email = user.getUsername();

        return Jwts.builder()
                .subject(email)
                .claim("role", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(new Date().getTime() + jwtExpiration * 1000L))
                .signWith(this.getKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        logger.info("Generating refresh token for user: {}", user.getUsername());
        String username = user.getUsername();

        return Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(new Date().getTime() + jwtExpiration * 1000L * 24 * 30 * 6))
                .signWith(this.getKey())
                .compact();
    }

    private List<String> getRolesFromAuth(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
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
            logger.error("Invalid JWT Token during validation", malformedJwtException);
            throw new MalformedJwtException("Invalid JWT Token");
        }

    }


    public String extractEmail(String token) {

        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public String refreshToken(String refreshToken, HttpServletRequest request) {
        boolean validateToken = validateToken(refreshToken, request);
        return null;
    }


    public String getToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;

    }
}
