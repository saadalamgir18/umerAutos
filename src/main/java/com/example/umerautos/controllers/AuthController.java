package com.example.umerautos.controllers;

import com.example.umerautos.dto.*;
import com.example.umerautos.globalException.RunTimeException;
import com.example.umerautos.services.JwtService;
import com.example.umerautos.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequestDTO request) {

        LoginResponse loginResponse = userService.login(request);

        ResponseCookie cookie = ResponseCookie.from("token", loginResponse.accessToken())
                .httpOnly(false)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(24 * 60 * 60 * 30 * 6)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", loginResponse.refreshToken())
                .httpOnly(false)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(24 * 60 * 60 * 30 * 6)
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(loginResponse);


    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request) {


        LoginResponse loginResponse = userService.refreshToken(request);
        ResponseCookie cookie = ResponseCookie.from("token", loginResponse.accessToken())
                .httpOnly(false)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(24 * 60 * 60)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", loginResponse.refreshToken())
                .httpOnly(false)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(24 * 60 * 60 * 30 * 6)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(loginResponse);

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {

        UserSignupResponse updatedUser = userService.getUserById(id);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {

            throw new RunTimeException();
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request, @PathVariable UUID id) {

        UserSignupResponse updatedUser = userService.updateUser(request, id);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {

            throw new RunTimeException();
        }

    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String token = jwtService.getToken(request);
        if (token != null && jwtService.validateToken(token, request)) {
            String username = jwtService.extractEmail(token);
            List<String> role = jwtService.getRoles(token);
            return ResponseEntity.ok(Map.of("username", username, "role", role));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserSignupResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);

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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/me/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);

        return new ResponseEntity<>("user deleted successfully!", HttpStatus.ACCEPTED);


    }


}
