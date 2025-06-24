package com.example.umerautos.services;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface JwtService {

    String generateToken(Authentication authentication);

    boolean validateToken(String token, HttpServletRequest request);

    String getToken(HttpServletRequest request);

    Claims extractPayload(String token);

    String getUsername(String token);

    List<String> getRoles(String token);

    String extractEmail(String token);
}
