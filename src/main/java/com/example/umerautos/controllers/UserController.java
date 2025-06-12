package com.example.umerautos.controllers;

import com.example.umerautos.dto.UserLoginRequestDTO;
import com.example.umerautos.dto.UserSignupRequest;
import com.example.umerautos.dto.UserSignupResponse;
import com.example.umerautos.services.JwtService;
import com.example.umerautos.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        if (authentication.isAuthenticated()) {


            String token = jwtService.generateToken(authentication.getName(), authentication.getAuthorities());


            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .sameSite("Lax")
                    .maxAge(24 * 60 * 60)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(Map.of("message", "Login successful"));
        } else {
            return new ResponseEntity<>("Auth not sucessfull!", HttpStatus.UNAUTHORIZED);

        }


    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {

        UserSignupResponse newUser = userService.signup(request);
        if (newUser != null) {
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);

        }


    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String token = jwtService.getToken(request);
        if (token != null && jwtService.validateToken(token, request)) {
            Claims claims = jwtService.extractPayload(token);
            String username = jwtService.getUsername(token);
            List<String> role = jwtService.getRoles(token);
            return ResponseEntity.ok(Map.of("username", username, "role", role));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)    // Match the original settings
                .secure(true)      // Match the original settings
                .path("/")         // Match the original settings
                .sameSite("Lax")   // Match the original settings
                .maxAge(0)         // Delete the cookie
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().body("Logged out successfully");
    }


}
