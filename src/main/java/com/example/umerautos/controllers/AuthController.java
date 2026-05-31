package com.example.umerautos.controllers;

import com.example.umerautos.dto.*;
import com.example.umerautos.globalException.RunTimeException;
import com.example.umerautos.services.JwtService;
import com.example.umerautos.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequestDTO request) {
        logger.info("Login request received for user: {}", request.email());
        try {
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

            logger.info("User logged in successfully: {}", request.email());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body(loginResponse);
        } catch (Exception e) {
            logger.error("Login failed for user: {}", request.email(), e);
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request) {
        logger.info("Refresh token request received");
        try {
            LoginResponse loginResponse = userService.refreshToken(request);
            logger.info("Token refreshed successfully");
            return ResponseEntity.ok()
                    .body(loginResponse);
        } catch (Exception e) {
            logger.error("Token refresh failed", e);
            throw e;
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {
        logger.info("Signup request received for user: {}", request.email());
        try {
            UserSignupResponse newUser = userService.signup(request);
            if (newUser != null) {
                logger.info("User signed up successfully: {}", newUser.email());
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } else {
                logger.error("Signup failed for user: {}", request.email());
                return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception during signup for user: {}", request.email(), e);
            throw e;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        logger.info("Request to get user by ID: {}", id);
        try {
            UserSignupResponse updatedUser = userService.getUserById(id);
            if (updatedUser != null) {
                logger.info("User found with ID: {}", id);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                logger.error("User not found with ID: {}", id);
                throw new RunTimeException();
            }
        } catch (Exception e) {
            logger.error("Exception while fetching user by ID: {}", id, e);
            throw e;
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long id) {
        logger.info("Request to update user with ID: {}", id);
        try {
            UserSignupResponse updatedUser = userService.updateUser(request, id);
            if (updatedUser != null) {
                logger.info("User updated successfully with ID: {}", id);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                logger.error("Failed to update user with ID: {}", id);
                throw new RunTimeException();
            }
        } catch (Exception e) {
            logger.error("Exception while updating user with ID: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        logger.info("Request to get current user details");
        try {
            String token = jwtService.getToken(request);
            if (token != null && jwtService.validateToken(token, request)) {
                String username = jwtService.extractEmail(token);
                UserSignupResponse user = userService.getUserByEmail(username);
                logger.info("Current user details retrieved for: {}", username);
                return ResponseEntity.ok(user);
            }
            logger.warn("Unauthorized access attempt to /me endpoint");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            logger.error("Exception while fetching current user details", e);
            throw e;
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        logger.info("Request to get all users");
        try {
            List<UserSignupResponse> users = userService.getAllUsers();
            logger.info("Retrieved {} users", users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while fetching all users", e);
            throw e;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        logger.info("Logout request received");
        try {
            ResponseCookie cookie = ResponseCookie.from("token", "")
                    .httpOnly(true)    // Match the original settings
                    .secure(true)      // Match the original settings
                    .path("/")         // Match the original settings
                    .sameSite("Lax")   // Match the original settings
                    .maxAge(0)         // Delete the cookie
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            logger.info("User logged out successfully");
            return ResponseEntity.ok().body("Logged out successfully");
        } catch (Exception e) {
            logger.error("Exception during logout", e);
            throw e;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/me/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Request to delete user with ID: {}", id);
        try {
            userService.deleteUser(id);
            logger.info("User deleted successfully with ID: {}", id);
            return new ResponseEntity<>("user deleted successfully!", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("Exception while deleting user with ID: {}", id, e);
            throw e;
        }
    }
}
