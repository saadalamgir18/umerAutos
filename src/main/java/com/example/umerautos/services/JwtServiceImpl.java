package com.example.umerautos.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.secret-key}")
    private String SECRET;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private UserDetailServiceImpl userDetailsService;

    public JwtServiceImpl(UserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(this.SECRET.getBytes(StandardCharsets.UTF_8));

    }

    public String getUsername(String token) {
        return extractPayload(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return extractPayload(token).get("role", List.class);
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
    public String generateToken(String userName, Collection<? extends GrantedAuthority> role) {

        List<String> roles = role.stream().map(role1 -> role1.getAuthority()).toList();
        return Jwts.builder()
                .subject(userName)
                .claim("userName", userName)
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

        String email = this.extractEmail(token);
        System.out.println(email);
        if (email == null) {
            return false;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails.getUsername().equals(email) && !isTokenExpired(token)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return true;
        }

        return false;
    }


    private String extractEmail(String token) {

        return extractClaim(token, Claims::getSubject);
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
