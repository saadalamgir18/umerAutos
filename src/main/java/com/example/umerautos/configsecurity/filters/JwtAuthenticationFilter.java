package com.example.umerautos.configsecurity.filters;

import com.example.umerautos.services.JwtService;
import com.example.umerautos.services.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Processing request in JwtAuthenticationFilter for: {}", request.getRequestURI());
        String path = request.getRequestURI();

        // ✅ Skip JWT validation for Actuator
        if (path.startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = this.getTokenFromRequest(request);

        if (token == null) {
            logger.warn("No JWT token found in request headers");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            if (jwtService.validateToken(token, request)) {
                String email = jwtService.extractEmail(token);
                logger.info("JWT token is valid. Extracted email: {}", email);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    if (userDetails.getUsername().equals(email)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        logger.info("User authenticated and security context updated for: {}", email);
                    }
                }
                filterChain.doFilter(request, response);
            } else {
                logger.warn("JWT token validation failed");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("Error during JWT token processing", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean shouldNotFilter = path.equals("/api/auth/signup") || path.equals("/api/auth/login");
        if (shouldNotFilter) {
            logger.info("Skipping JwtAuthenticationFilter for path: {}", path);
        }
        return shouldNotFilter;
    }

}
