package com.example.umerautos.services;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface JwtService {

    String generateToken(String userName, Collection<? extends GrantedAuthority> role);

    boolean validateToken(String token, HttpServletRequest request);

    String getToken(HttpServletRequest request);

    Claims extractPayload(String token);

    String getUsername(String token);

    List<String> getRoles(String token);
}
